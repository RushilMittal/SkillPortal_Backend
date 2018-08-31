import { baseURLAPP } from "../baseUrl";

export let CONFIG = {
    Settings: {
      BASEAPI: 'https://localhost:44356/api', // .NetCore2.0 WebAPI
      CLIENT_ID: 'edb31c7a-1273-44e8-b0d0-50830aaede35', // https://apps.dev.microsoft.com’
      TENANT: 'ALLEGIS CLOUD',
      TENANT_ID: '371cb917-b098-4303-b878-c182ec8403ac', // found in Azure->Active Directory->Properties Pane, TennantID = Directory ID
      AUTHORITY: 'https://login.microsoftonline.com/371cb917-b098-4303-b878-c182ec8403ac',
  
      RESPONSE_TYPE: 'id_token',
      RESPONSE_MODE: 'id_token',
      STATE: '',
      MS_GRAPH_URI: 'https://graph.microsoft.com/',
      REDIRECT_URI : 'http://localhost:4200/dashboard',
      POST_LOGOUT_REDIRECT : 'http://localhost:4200',
      MSGRAPH_BETA_API: 'https://graph.microsoft.com/beta/',
      MSGRAPH_v1_API: 'https://graph.microsoft.com/v1.0/',
      SCOPES: ['user.read','Mail.Send' ],
      ADMIN_CONSENT: true
    }
  };
  