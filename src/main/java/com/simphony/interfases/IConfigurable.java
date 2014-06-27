package com.simphony.interfases;

import java.text.SimpleDateFormat;

public interface IConfigurable {

    
    public final SimpleDateFormat _SDFCHART = new SimpleDateFormat("yyyyMM");
    public final SimpleDateFormat _SDFCHARTLEGEND = new SimpleDateFormat("MMM yyyy");
    public final SimpleDateFormat _SDF = new SimpleDateFormat("yyyyMMdd");
    public final SimpleDateFormat _SHF = new SimpleDateFormat("hhmmss");
    public final SimpleDateFormat _SGF = new SimpleDateFormat("yyyyMMdd hhmmss");
    public final String _BLANK = "";
    public final String _ROOT_IMG = "ROOTIMG";
            

    //Global status
    public final String _ENABLED = "A";
    public final String _DISABLE = "B";
    
    //Tipos usuario
    public final String _USR_TYPE_ADMIN = "AD";
    public final String _USR_TYPE_VENDOR = "VD";
    
    //Acciones 
    public final String _ADD = "A";
    public final String _MODIFY = "M";

    //Areas usuario
    public final String _USR_AREA_IT = "IT";
    public final String _USR_AREA_QUALITY = "QL";
    public final String _USR_AREA_MARKETING = "MK";
    public final String _USR_AREA_PLANT = "PL";
    public final String _USR_AREA_RANDD = "RD";
    public final String _USR_AREA_PACKAGING = "PK";

    //NCR Tipos
    public final String _NCR_TYPE_FAMILY = "F";
    public final String _NCR_TYPE_COMPONENT = "C";
    public final String _NCR_TYPE_DEFECT = "D";

    //Prefijos de los defectos
    public final String _PREFIX_DEFECT_FAMILY = "FAM";
    public final String _PREFIX_DEFECT_COMPONENT = "COM";
    public final String _PREFIX_DEFECT_DEFECT = "DEF";

    //Directorios para guardar documentos
    public final String _DIR_DOC_ROOT = "C:\\Jafra-Claims\\";
    public final String _DIR_DOC_TEMP = "C:\\Jafra-Claims\\temp\\";
    public final String _DIR_DOC_CAPTURE = "Capture\\";
    public final String _DIR_DOC_QRQC = "QRQC\\";
    public final String _DIR_DOC_ANALIST = "Analist\\";

    public final String _DIR_DOC_AREA = "Area\\";
    public final String _DIR_DOC_DOCUMENTERS = "Documenters\\";
    public final String _DIR_DOC_TICKETS = "Tickets\\";
    public final String _DIR_DOC_FINAL_RESONSE = "Final-Response\\";
    public final String _DIR_NOIMAGE = "C:\\Jafra-Claims\\NoImage.jpg";

    //Lenguajes
    public final String _LANGUAGE_MEXICAN = "ES";
    public final String _LANGUAGE_ENGLISH = "EN";
    public final String _LANGUAGE_GERMANY = "DE";

    //Mercados
    public final String _COUNTRY_MEXICO = "MX";
    public final String _COUNTRY_USA = "US";
    public final String _COUNTRY_GERMANY = "EU";

    //Email Configuration
    public final String SMTP_HOST_NAME = "172.16.21.15";
    public final int SMTP_HOST_PORT = 25;
    public final String SMTP_AUTH_USER = "QRQC-TEAM";
    public final String SMTP_AUTH_PWD = "desarrollo";
    public final String _SUBJECT_MAIL = "QRQC Management System";

    //Topicos para mensaje de correo
    public final String _REQUEST_INITIAL_USER = "IU";
    public final String _REQUEST_PASSWORD = "RP";
    public final String _ANALIZER_NCR_REQUEST = "ARN";

    //Trace
    public final String _FINAL_RESPONSE_TRACE = "FR";
    public final String _FINAL_INITIALDOC_TRACE = "ID";
    public final String _CREATED_CLAIM_TRACE = "CR";

    //Source Type
    public final String _SOURCE_NCR = "NCR";
    public final String _SOURCE_BINCHK = "BCHK";
    public final String _SOURCE_PPM = "PPM";
    // Simple Yes or NO
    public final String _YES = "S";
    public final String _NO = "N";
    
    //Estatus del Ticket
    public final String _TCK_CREATED = "A";
    public final String _TCK_CLOSED = "C";
    public final String _TCK_PROGRESS = "P";
    
    //Casos de navegación general
    public final String _NAV_TO_INDEX = "toindex";
    
    //Documentos
    public final String _COMMITTED = "CO";
    public final String _UNCOMMITTED = "UN";
    
    public final String _DOC_REGULATORY = "UN";
    public final String _DOC_PHYSICOCHEMICAL = "FS";
    public final String _DOC_MICROBIOLOGY = "MI";
    public final String _DOC_ENGINERRING_COMP = "IC";
    public final String _DOC_QRQC = "QR";
    public final String _DOC_QUALITY_SUP = "SC";
    public final String _DOC_MAINTENANCE = "MT";
    public final String _DOC_MAINTENANCE_PLAN = "PM";
    
    //Atenciones
    public final String _ATTENTION_PER_ATTENTION = "PA";
    public final String _ATTENTION_ATTENDED = "AT";
    public final String _ATTENTION_CLOSED = "CL";
    

}
