import { Component } from '@angular/core';

@Component({
  selector: 'app-achete',
  templateUrl: './achete.component.html',
  styleUrls: ['./achete.component.css']
})
export class AcheteComponent {


//   import { Component } from '@angular/core';
// import { CommonModule } from '@angular/common';

// @Component({
//   selector: 'app-root',
//   standalone: true,
//   imports: [CommonModule],
//   templateUrl: './app.component.html',
//   styleUrls: ['./app.component.css']
// })

  whatsappNumber: string = '0766498601';
  meterNumber: string = '4885060';
  lastUpdate: Date = new Date();

  get whatsappNumberClean(): string {
    // Nettoie le numéro pour WhatsApp (enlève le 0 initial si nécessaire)
    let cleanNumber = this.whatsappNumber.replace(/[\s\-\(\)]/g, '');
    if (cleanNumber.startsWith('0')) {
      cleanNumber = '212' + cleanNumber.substring(1);
    }
    return cleanNumber;
  }

  constructor() {
    // Charger le numéro de compteur depuis le stockage local ou API
    // this.loadMeterNumber();
  }

  loadMeterNumber() {
    // Exemple: récupérer depuis localStorage
    const savedMeter = localStorage.getItem('meterNumber');
    if (savedMeter) {
      this.meterNumber = savedMeter;
    } else {
      // Numéro de compteur par défaut
      this.meterNumber = 'CMP-' + Math.floor(Math.random() * 1000000);
    }
  }

  copyMeterNumber() {
    if (this.meterNumber) {
      navigator.clipboard.writeText(this.meterNumber).then(() => {
        this.showToast('✅ Numéro de compteur copié !');
      }).catch(err => {
        console.error('Erreur de copie:', err);
        this.showToast('❌ Erreur lors de la copie');
      });
    }
  }

  sendQuickMessage() {
    const message = encodeURIComponent('Bonjour, je souhaiterais avoir des informations sur mon compteur.');
    // const whatsappUrl = `https://wa.me/${this.whatsappNumberClean}?text=${message}`;
    const whatsappUrl = `https://wa.me/${this.whatsappNumberClean}`;
    window.open(whatsappUrl, '_blank');
  }

  refreshData() {
    this.showToast('🔄 Actualisation des données...');
    setTimeout(() => {
      this.lastUpdate = new Date();
      this.showToast('✅ Données actualisées avec succès !');
    }, 1000);
  }

  showToast(message: string) {
    const toast = document.createElement('div');
    toast.className = 'toast';
    toast.textContent = message;
    document.body.appendChild(toast);
    
    setTimeout(() => {
      toast.remove();
    }, 3000);
  }
}


