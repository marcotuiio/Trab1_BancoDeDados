package org.example;


import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.HashMap;
import java.util.Map;
@JsonIgnoreProperties(ignoreUnknown = true)
public class SerieAnoAtrib {
    @JsonAlias({"-", "1990", "1990-1995", "1995", "1995-2000", "1999-2001", "2000", "2000-2002", "2000-2005", "2001", "2001-2003", "2002", "2002-2004", "2003", "2003-2005", "2004", "2004-2006", "2005", "2005-2007", "2005-2010", "2006", "2006-2008", "2007", "2007-2009", "2008", "2008-2010", "2009", "2009-2011", "2010", "2010-2012", "2010-2015", "2011", "2011-2013", "2012", "2012-2014", "2013", "2013-2015", "2014", "2014-2016", "2015", "2015-2017", "2015-2020", "2016", "2016-2018", "2017", "2017-2019", "2018", "2018-2020", "2019", "2020", "2021", "2022"})
    private Map<String, String> duplaAnoAtributo = new HashMap<>();

    public SerieAnoAtrib(String ano, String atributo) {
        this.duplaAnoAtributo.put(ano, atributo);
    }

    public Map<String, String> getDuplaAnoAtributo() {
        return duplaAnoAtributo;
    }

    public void setDuplaAnoAtributo(Map<String, String> value) {
        this.duplaAnoAtributo = value;
    }
}