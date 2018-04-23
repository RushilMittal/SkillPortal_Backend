import { Injectable } from "@angular/core";
import { Ng2IzitoastService } from "ng2-izitoast";

//Service for displaying the responsive Toast.
@Injectable()
export class ToastService {
    constructor(private toast: Ng2IzitoastService) { }
    setDefault() {
        this.toast.settings({
            timeout: 3500,
            title:'Skill Portal',
            transitionIn: 'flipInX',
            transitionOut: 'flipOutX',
            theme:'light',
            pauseOnHover:true,
            position:'topRight',
            maxWidth: '350px'
            
        });
    }
    //setting default property for the Success Toast
    setDefaultSuccess() {
        this.toast.settings({
            backgroundColor: '#90EE90',
            icon:'fa fa-check'
        });
    }
    //setting default property for the Warn Toast
    setDefaultWarn(){
        this.toast.settings({
            backgroundColor:'#FF9226',
            icon: 'fa fa-exclamation-triangle'
        });
    }
    //setting default property for the Info Toast
    setDefaultInfo(){
        this.toast.settings({
            backgroundColor:'skyblue',
            icon: 'fa fa-info-circle'
        });
    }
    //setting default property for the Error Toast
    setDefaultError(){
        this.toast.settings({
            backgroundColor:'#FF4C4C',
            icon:'fa fa-ban'
        })
    }

    // method for displying the Success Toast
    showSuccessToast(message: string) {
        this.setDefault();
        this.setDefaultSuccess();
        this.toast.show({
            message: message
        });
    }
    // method for displying the warn Toast
    showWarnToast(message:string){
        this.setDefault();
        this.setDefaultWarn();
        this.toast.show({
            message:message
        });
    }
    // method for displying the Error Toast
    showErrorToast(message:string){
        this.setDefault();
        this.setDefaultError();
        this.toast.show({
            message:message
        });
    }
    // For displying the Info Toast
    showInfoToast(message: string){
        this.setDefault();
        this.setDefaultInfo();
        this.toast.show({
            message:message
        });
    }
}