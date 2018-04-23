export class TrainingSession {
     trainingId: string;
    trainingDate: Date;
    startTime: string;
    endTime: string;

   constructor(trainingId, trainingDate, startTime, endTime) {
      this.trainingId = trainingId;
       this.trainingDate = trainingDate;
       this.startTime = startTime;
       this.endTime = endTime;
   }

}
