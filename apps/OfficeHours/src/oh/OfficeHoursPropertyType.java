package oh;

/**
 * This class provides the properties that are needed to be loaded for
 * setting up To Do List Maker workspace controls including language-dependent
 * text.
 * 
 * @author Richard McKenna
 * @version 1.0
 */
public enum OfficeHoursPropertyType {

    /* THESE ARE THE NODES IN OUR APP */
    // FOR SIMPLE OK/CANCEL DIALOG BOXES
    OH_OK_PROMPT,
    OH_CANCEL_PROMPT,

    // THESE ARE FOR TEXT PARTICULAR TO THE APP'S WORKSPACE CONTROLS
    OH_LEFT_PANE,
    OH_TAS_HEADER_PANE,
    OH_TAS_HEADER_LABEL,
    OH_TAS_HEADER_TEXT_FIELD,
    OH_TAS_TABLE_VIEW,
    OH_NAME_TABLE_COLUMN,
    OH_EMAIL_TABLE_COLUMN,
    OH_SLOTS_TABLE_COLUMN,
    OH_TA_BUTTONS_PANE,
    OH_UNDERGRADUATE_TA_BUTTON,
    OH_GRADUATE_TA_BUTTON,
    OH_ALL_TA_BUTTON,
    OH_TA_TYPE_TABLE_COLUMN,
    OH_ADD_UNDERGRADUATE_TA_BUTTON,
    OH_ADD_GRADUATE_TA_BUTTON,
    
    OH_NAME_PROMPT,
    OH_EMAIL_PROMPT,

    OH_ADD_TA_PANE,
    OH_NAME_TEXT_FIELD,
    OH_ADD_TA_BUTTON,
    OH_EMAIL_TEXT_FIELD,
    INVALID_EMAIL_ERROR,

    OH_RIGHT_PANE,
    OH_OFFICE_HOURS_HEADER_PANE,
    OH_OFFICE_HOURS_HEADER_LABEL,
    OH_OFFICE_HOURS_TABLE_VIEW,
    OH_START_TIME_TABLE_COLUMN,
    OH_END_TIME_TABLE_COLUMN,
    OH_MONDAY_TABLE_COLUMN,
    OH_TUESDAY_TABLE_COLUMN,
    OH_WEDNESDAY_TABLE_COLUMN,
    OH_THURSDAY_TABLE_COLUMN,
    OH_FRIDAY_TABLE_COLUMN,
    OH_DAYS_OF_WEEK,
    OH_FOOLPROOF_SETTINGS,

    // THESE ARE FOR ERROR MESSAGES PARTICULAR TO THE APP
    OH_MISSING_TA_NAME_TITLE_LABEL,
    OH_MISSING_TA_NAME_MESSAGE,
}