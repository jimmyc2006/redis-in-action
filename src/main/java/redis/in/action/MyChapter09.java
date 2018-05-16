package redis.in.action;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import redis.clients.jedis.Jedis;

/**
 * @author shuwei
 * @version 创建时间：2017年7月4日 上午8:58:42
 * 类说明
 */
public class MyChapter09 {
    private static final String[] COUNTRIES =
            ("ABW AFG AGO AIA ALA ALB AND ARE ARG ARM ASM ATA ATF ATG AUS AUT AZE BDI "
                    + "BEL BEN BES BFA BGD BGR BHR BHS BIH BLM BLR BLZ BMU BOL BRA BRB BRN BTN "
                    + "BVT BWA CAF CAN CCK CHE CHL CHN CIV CMR COD COG COK COL COM CPV CRI CUB "
                    + "CUW CXR CYM CYP CZE DEU DJI DMA DNK DOM DZA ECU EGY ERI ESH ESP EST ETH "
                    + "FIN FJI FLK FRA FRO FSM GAB GBR GEO GGY GHA GIB GIN GLP GMB GNB GNQ GRC "
                    + "GRD GRL GTM GUF GUM GUY HKG HMD HND HRV HTI HUN IDN IMN IND IOT IRL IRN "
                    + "IRQ ISL ISR ITA JAM JEY JOR JPN KAZ KEN KGZ KHM KIR KNA KOR KWT LAO LBN "
                    + "LBR LBY LCA LIE LKA LSO LTU LUX LVA MAC MAF MAR MCO MDA MDG MDV MEX MHL "
                    + "MKD MLI MLT MMR MNE MNG MNP MOZ MRT MSR MTQ MUS MWI MYS MYT NAM NCL NER "
                    + "NFK NGA NIC NIU NLD NOR NPL NRU NZL OMN PAK PAN PCN PER PHL PLW PNG POL "
                    + "PRI PRK PRT PRY PSE PYF QAT REU ROU RUS RWA SAU SDN SEN SGP SGS SHN SJM "
                    + "SLB SLE SLV SMR SOM SPM SRB SSD STP SUR SVK SVN SWE SWZ SXM SYC SYR TCA "
                    + "TCD TGO THA TJK TKL TKM TLS TON TTO TUN TUR TUV TWN TZA UGA UKR UMI URY "
                    + "USA UZB VAT VCT VEN VGB VIR VNM VUT WLF WSM YEM ZAF ZMB ZWE").split(" ");

    private static final Map<String, String[]> STATES = new HashMap<String, String[]>();
    static {
        STATES.put("CAN", "AB BC MB NB NL NS NT NU ON PE QC SK YT".split(" "));
        STATES.put("USA", ("AA AE AK AL AP AR AS AZ CA CO CT DC DE FL FM GA GU HI IA ID IL IN "
                + "KS KY LA MA MD ME MH MI MN MO MP MS MT NC ND NE NH NJ NM NV NY OH "
                + "OK OR PA PR PW RI SC SD TN TX UT VA VI VT WA WI WV WY").split(" "));
    }
    
    public String getCode(String country, String states) {
        int cindex = Arrays.binarySearch(COUNTRIES, country);
        System.out.println("cindex:" + cindex);
        int sindex = Arrays.binarySearch(STATES.get(country), states);
        System.out.println("sindex:" + sindex);
        return new String(new char[] {(char) cindex, (char) sindex});
    }
    
    private long USERS_PER_SHARD = 5;
    
    public void setLocation(Jedis conn, long userId, String country, String state) {
        String code = getCode(country, state);
        
        long shardId = userId / USERS_PER_SHARD;
        int position = (int) (userId % USERS_PER_SHARD);
        
    }
    
    public static void main(String[] args) {
        MyChapter09 mc9 = new MyChapter09();
        System.out.println(mc9.getCode("USA", "WY"));
        System.out.println("---");
        System.out.println((char)12345677);
    }
}
