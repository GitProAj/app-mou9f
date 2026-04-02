// sidebar.service.ts
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { NavigationEnd, Router } from '@angular/router';
import { filter } from 'rxjs/operators';

export interface SidebarMenuItem {
  id: string;
  label: string;
  icon: string;
  link?: string;
  children?: SidebarMenuItem[];
  permissions?: string[];
  isVisible?: boolean;
  expanded?: boolean;
  badge?: string | number;
  badgeColor?: string;
}

@Injectable({
  providedIn: 'root'
})
export class SidebarService {
  private menuItems = new BehaviorSubject<SidebarMenuItem[]>([]);
  private isCollapsed = new BehaviorSubject<boolean>(false);
  private activeItem = new BehaviorSubject<string>('');

  constructor(private router: Router) {
    this.initializeMenu();
    this.trackActiveRoute();
  }

  private initializeMenu() {
    const menuItems: SidebarMenuItem[] = [
      {
        id: 'dashboard',
        label: 'Dashboard',
        icon: 'dashboard',
        link: '/dashboard',
        badge: 'New',
        badgeColor: 'primary'
      },
      {
        id: 'analytics',
        label: 'Analytics',
        icon: 'analytics',
        link: '/analytics'
      },
      {
        id: 'user-management',
        label: 'User Management',
        icon: 'people',
        children: [
          {
            id: 'users',
            label: 'Users',
            icon: 'person',
            link: '/users',
            badge: 24,
            badgeColor: 'accent'
          },
          {
            id: 'roles',
            label: 'Roles & Permissions',
            icon: 'security',
            link: '/roles'
          },
          {
            id: 'activity',
            label: 'Activity Log',
            icon: 'history',
            link: '/activity'
          }
        ]
      },
      {
        id: 'content',
        label: 'Content Management',
        icon: 'article',
        children: [
          {
            id: 'posts',
            label: 'Posts',
            icon: 'post_add',
            link: '/posts'
          },
          {
            id: 'categories',
            label: 'Categories',
            icon: 'category',
            link: '/categories'
          },
          {
            id: 'media',
            label: 'Media Library',
            icon: 'collections',
            link: '/media'
          }
        ]
      },
      {
        id: 'settings',
        label: 'Settings',
        icon: 'settings',
        children: [
          {
            id: 'profile',
            label: 'Profile',
            icon: 'account_circle',
            link: '/settings/profile'
          },
          {
            id: 'preferences',
            label: 'Preferences',
            icon: 'tune',
            link: '/settings/preferences'
          },
          {
            id: 'security',
            label: 'Security',
            icon: 'lock',
            link: '/settings/security'
          }
        ]
      }
    ];

    this.menuItems.next(menuItems);
  }

  private trackActiveRoute() {
    this.router.events
      .pipe(filter(event => event instanceof NavigationEnd))
      .subscribe(() => {
        const url = this.router.url;
        this.setActiveItem(url);
      });
  }

  private setActiveItem(url: string) {
    this.activeItem.next(url);
  }

  getMenuItems(): Observable<SidebarMenuItem[]> {
    return this.menuItems.asObservable();
  }

  toggleCollapsed(): void {
    this.isCollapsed.next(!this.isCollapsed.value);
  }

  getCollapsedState(): Observable<boolean> {
    return this.isCollapsed.asObservable();
  }

  filterMenuByPermission(permissions: string[]): void {
    const filterMenu = (items: SidebarMenuItem[]): SidebarMenuItem[] => {
      return items
        .map(item => ({
          ...item,
          children: item.children ? filterMenu(item.children) : undefined,
          isVisible: !item.permissions || 
                     item.permissions.some(p => permissions.includes(p))
        }))
        .filter(item => item.isVisible || 
               (item.children && item.children.length > 0));
    };

    const currentItems = this.menuItems.value;
    const filteredItems = filterMenu(currentItems);
    this.menuItems.next(filteredItems);
  }

  searchMenuItems(query: string): SidebarMenuItem[] {
    const searchInItems = (items: SidebarMenuItem[]): SidebarMenuItem[] => {
      return items.reduce((acc: SidebarMenuItem[], item) => {
        const matchesSearch = item.label.toLowerCase().includes(query.toLowerCase());
        
        if (matchesSearch) {
          acc.push({ ...item, expanded: true });
        } else if (item.children) {
          const filteredChildren = searchInItems(item.children);
          if (filteredChildren.length > 0) {
            acc.push({ ...item, children: filteredChildren, expanded: true });
          }
        }
        
        return acc;
      }, []);
    };

    return searchInItems(this.menuItems.value);
  }
}
