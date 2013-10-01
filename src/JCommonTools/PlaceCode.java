package JCommonTools;

/** 
 * Класс для хранения составного идентификатора.
 * Состоит из двух целых чисел (Int32 - 4 байта) - place и code.
 * @author M.Tor
 * @version 1.0
 */
public class PlaceCode implements Cloneable {

    /** 
    * Константа разделителя для строкового представления идентификатора
    * в виде "place[DELIM]code"
    */ 
    public final static char DELIM = ':';
    
    /**
    * Первая составляющая идентификатора.
    */
    public int place;
    /**
    * Вторая составляющая идентификатора.
    */
    public int code;
    
    public PlaceCode()
    {
    	this (0, 0);
    }
    public PlaceCode(int aPlace, int aCode)
    {
      place = aPlace;
      code = aCode;
    }
    
    /**
    * Определено ли значение класса
    */
    public Boolean IsDefined()
    {
      return place > 0 && code > 0;
    }

    //public static final PlaceCode Empty = new PlaceCode(0, 0);
    
    @Override
    public boolean equals(Object arg0) {
        PlaceCode PC = (PlaceCode)arg0;
        return this.code == PC.code && this.place == PC.place;
    }
    
    @Override
    public String toString() {
        return "place=" + place + ", code=" + code;
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
    	//PlaceCode pc = new PlaceCode();
    	//pc.place = this.place;
    	//pc.code = this.code;
    	return super.clone();
    }
    @Override
    public int hashCode() {
    	return PlaceCode2PDC(this).hashCode();
    }
    
    public String getPlaceDelimCode()
    {
    	return PlaceCode2PDC(this);
    }
    //public void setPlaceDelimCode(String aVal)
    //{
    //	this = PDC2PlaceCode(aVal);
    //}
    
    public static String PlaceCode2PDC(PlaceCode aPC)
    {
      return ((Integer)aPC.place).toString() + PlaceCode.DELIM + ((Integer)aPC.code).toString();
    }

    public static PlaceCode PDC2PlaceCode(String aPDC)
    {
      PlaceCode pc = new PlaceCode();
      if (aPDC != null && aPDC.length() > 0)
      {
        String[] s = aPDC.split(Character.toString(DELIM));
        if (s.length == 2)
        {
          pc.place = Integer.parseInt(s[0]);
          pc.code = Integer.parseInt(s[1]);
        }
      }
      return pc;
    }
}
