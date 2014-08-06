package com.simphony.interfases;

import java.text.SimpleDateFormat;

public interface IConfigurable {

    
    public final SimpleDateFormat _SDFCHART = new SimpleDateFormat("yyyyMM");
    public final SimpleDateFormat _SDFCHARTLEGEND = new SimpleDateFormat("MMM yyyy");
    public final SimpleDateFormat _SDF = new SimpleDateFormat("yyyyMMdd");
    public final SimpleDateFormat _SHF = new SimpleDateFormat("hhmmss");
    public final SimpleDateFormat _SHM = new SimpleDateFormat("hh:mm");
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

    public final String _GUIDE_TYPE_OPEN = "OP";
    public final String _GUIDE_TYPE_CLOSED = "CL";

    public final String _SALE_TYPE_PUBLIC = "P";
    public final String _SALE_TYPE_ASSOCIATE = "A";
    
    // Simple Yes or NO
    public final String _YES = "S";
    public final String _NO = "N";
    
    
    //Casos de navegación general
    public final String _NAV_TO_INDEX = "toindex";

}
