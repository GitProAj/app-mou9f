export class Video {
    id: number;                    // Identifiant unique
    titre: string;                 // Titre de la vidéo
    description: string;           // Description
    typeVideo: string;             // Type MIME (video/mp4, etc.)
    tailleVideo: number;           // Taille en bytes
    dateUpload: Date;              // Date d'upload (sera convertie en objet Date)
    urlStreaming: string;           // URL pour streamer la vidéo

    constructor(id: number, titre: string, description: string,typeVideo: string,tailleVideo: number,dateUpload: Date,urlStreaming: string,){
                    this.id=id;
                    this.titre=titre;
                    this.description=description;
                    this.typeVideo=typeVideo;
                    this.tailleVideo=tailleVideo;
                    this.dateUpload=dateUpload;
                    this.urlStreaming=urlStreaming;
                }

}