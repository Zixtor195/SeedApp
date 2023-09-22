package app.android.seedapp.utils

object Constants {

    const val SHARED_PREFERENCES_NAME = "MySharedPreferences"
    const val DEVICE_TYPE = "android"

    const val URL_SEED_API = "http://referidos.othelo.com.co/api/"

    //Shared Preferences
    const val FIREBASE_TOKEN = "FirebaseToken"
    const val APPLICATION_TOKEN = "ApplicationToken"
    const val ACTIVE_CAMPAIGN = "ActiveCampaign"
    const val ACTIVE_CANDIDATE = "ActiveCandidate"
    const val USER_ID_INFO = "UserIdInfo"
    const val USER_INFO = "UserInfo"

    //Endpoint Urls
    const val URL_VALIDATE_NUMBER_ID = "validate-number-id"
    const val URL_VALIDATE_TYPE_PASSWORD = "validate-type-password/"
    const val URL_USER_LOGIN = "login/"
    const val URL_GET_DEPARTMENT_LIST = "department/"
    const val URL_GET_MUNICIPALITY_LIST = "municipality/"
    const val URL_SEND_CONDITION_CODE = "code-refered-user/"
    const val URL_REGISTER_USER = "user-referred/"
    const val URL_GET_USER_CAMPAIGNS = "get-campaings/"
    const val URL_GET_USER_INFO = "get-info-user/"
    const val URL_GET_USER_REFERRED_LIST = "get-user-refered/"
    const val URL_GET_CANDIDATES = "get-candidates/"
    const val URL_GET_USER_STATISTICS = "get-user-statics/"
    const val URL_GET_USER_LEADERS = "get-user-leaders/"
    const val URL_GET_USER_CVS = "get-user-csv/"

    //Authorization Strings
    const val HEADER_BASIC_AUTHORIZATION = "Authorization"
    const val TOKEN_AUTHORIZATION = "Token"


    const val USER_LEADER = "user_leader"
    const val TYPE_DATA = "type_data"
    const val START_DATE = "start_date"
    const val END_DATE = "end_date"
    const val CAMPAING_ID = "campaing_id"
    const val IS_CANDIDATE = "isCandidate"

    const val QUERY_NUMBER_DOCUMENT = "number_document"
    const val QUERY_ID_DOCUMENT_CANDIDATE = "idCandidate"
    const val QUERY_REGISTER_DATA = "register_data"
    const val QUERY_DEPARTMENT = "department"
    const val QUERY_ID_DOCUMENT = "id"
    const val QUERY_ID_KEY = "key"
    const val LEADER_NAME = "leader_name"
    const val LEADER_REPEAT = "leader_repeat"

    //Name app screens
    const val LOGIN_SCREEN = "login_screen"
    const val LOGIN_ACCESS_SCREEN = "login_access_screen"
    const val LOGIN_WEB_VIEW_SCREEN = "login_web_view_screen"

    const val USER_DETAILS_SCREEN = "user_details_screen"

    const val LEADER_REGISTERS_SCREEN = "leader_registers_screen"

    const val CAMPAIGNS_SELECTION_SCREEN = "campaigns_selection_screen"

    const val HOME_SCREEN = "home_screen"
    const val REGISTER_USER_SCREEN = "register_user_screen"
    const val REGISTER_USER_WEB_VIEW_SCREEN = "register_user_web_view_screen"
    const val REGISTER_USER_CODE_SCREEN = "register_user_code_screen"
    const val REGISTER_USER_ID_SCREEN = "register_user_id_screen"

    const val REGEX_EMAIL = "^[A-Za-z](.*)(@)(.+)(\\.)(.+)"

    /**Bottom Bar Routes**/
    const val REGISTER_SCREEN = "register_screen"
    const val REGISTER_TITLE = "Registro"
    const val GRAPHS_SCREEN = "graphs_screen"
    const val GRAPHS_TITLE = "Graficos"
    const val EVENTS_SCREEN = "events_screen"
    const val TOOLS_SCREEN = "tools_screen"

}