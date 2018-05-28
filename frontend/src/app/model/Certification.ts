export class Certification {
    id: string;
   skillId: string;
   certificationName: string;
   institution: string;
  
   constructor(id, skillId, certificationName, institution) {
       this.id = id;
       this.skillId = skillId;
       this.certificationName = certificationName;
       this.institution = institution;
   }
   }
