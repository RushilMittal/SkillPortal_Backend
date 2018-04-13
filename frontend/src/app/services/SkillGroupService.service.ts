import {Injectable} from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Observable} from 'rxjs/Observable';
import { SkillGroup } from '../model/SkillGroup';
import { baseUrlSkill } from '../baseUrl';
@Injectable()
export class SkillGroupService {
    url = baseUrlSkill + '/getallskillgroups';
    constructor(private httpClient: HttpClient) {}
    getData() {
       return this.httpClient.get<SkillGroup>(this.url);

    }
}
