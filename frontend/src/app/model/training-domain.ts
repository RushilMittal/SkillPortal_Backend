import { TrainingSession } from './training-sessions';
import { NewTraining } from './New-Training';

export class TrainingDomain {
   training: NewTraining;
   trainingSessions: TrainingSession[];
}
