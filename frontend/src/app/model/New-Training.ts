export class NewTraining {
    id: string;
    name: string;
    description: string;
    location: string;
    trainer: string;
    seats: number;
    type: string;


    constructor(id, name, description, location, trainer, seats, type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.trainer = trainer;
        this.seats = seats;
        this.type = type;
    }

}
