// Basic location model for the application
// Is used also as variable in class User (models/user/user.model.ts)

export class Location {
    id: number;
    location: string;
    latitude: number;
    longitude: number;
    country: string;
}
