// AUTH 
export const URL: string = "https://ubooku.herokuapp.com";
export const URL_AUTH: string = URL + "/auth";
export const URL_AUTH_LOGIN: string = URL_AUTH + "/sign-in";
export const URL_AUTH_REGISTER: string = URL_AUTH + "/sign-up";
export const URL_USER_INFO: string = URL + "/users/me";

export const OAUTH2_REDIRECT_URI = URL + "/home";

export const FACEBOOK_AUTH_URL = URL + "/oauth2/authorize/facebook?redirect_uri=" + OAUTH2_REDIRECT_URI;
export const GOOGLE_AUTH_URL = URL + "/oauth2/authorize/google?redirect_uri=" + OAUTH2_REDIRECT_URI;

export const ACCESS_TOKEN = "accessToken";

//UPLOAD
export const FILE_UPLOAD_URL = URL + "/upload/book";

//BOOK API CONST
export const BOOK_API_URL = URL + "/api/books";