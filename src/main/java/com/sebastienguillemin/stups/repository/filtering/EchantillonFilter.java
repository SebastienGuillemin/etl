package com.sebastienguillemin.stups.repository.filtering;

import java.util.List;

import com.sebastienguillemin.stups.util.PropertiesReader;

public class EchantillonFilter {
    private List<Integer> whiteList;
    private List<Integer> blackList;

    public EchantillonFilter(boolean STUPSevaluation) {
        PropertiesReader propertiesReader = PropertiesReader.getInstance();
        
        this.whiteList = propertiesReader.getWhiteList(STUPSevaluation);
        this.blackList = propertiesReader.getBlackList(STUPSevaluation);

        System.out.println("\nEchantillon filter initialised:");
        System.out.println("White list : " + this.whiteList);
        System.out.println("Black list : " + this.blackList);
        System.out.println();
    }

    /***
     * Return wether an Echantillon can be retrieved. Only the Enchantillon in the
     * white list (if any) are accepted or those that are not in the black list (if
     * any).
     * 
     * @param echantillonId
     * @return True if the Echantillon can be retrieved, False otherwise
     */
    public boolean filter(int echantillonId) {
        if (this.whiteList.size() > 0)
            return whiteList.contains(echantillonId);
        
        if  (this.blackList.size() > 0)
            return !this.blackList.contains(echantillonId);

        return true;
    }

}
