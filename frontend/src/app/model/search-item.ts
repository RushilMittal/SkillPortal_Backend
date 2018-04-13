export class SearchItem {
    skillId: string;
    name: string;
    isChild: boolean;
    subSkillId: string;

    constructor(skillId: string, name: string, isChild: boolean, subSkillId: string) {
       this.skillId = skillId;
       this.name = name;
       this.isChild = isChild;
       this.subSkillId = subSkillId;
    }

}
