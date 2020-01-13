export class JwtHelper {
  private urlBase64decode(token: string) {
    var output = token.replace(/-/g, "+").replace(/_/g, "/");
    // console.log(output);

    switch (output.length % 4) {
      case 0: {
        break;
      }
      case 2: {
        output += "==";
        break;
      }
      case 3: {
        output += "=";
        break;
      }
      default: {
        throw "Illegal base64url string!";
      }
    }
    return decodeURIComponent(encodeURI(window.atob(output)));
  }

  public decodeToken(token: string) {
    var parts = token.split(".");
    if (parts.length !== 3) {
      throw new Error("JWT must have 3 parts");
    }
    var decoded = this.urlBase64decode(parts[1]);
    if (!decoded) {
      throw new Error("Cannot decode the token");
    }
    return JSON.parse(decoded);
  }
}
