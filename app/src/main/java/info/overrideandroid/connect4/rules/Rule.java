package info.overrideandroid.connect4.rules;

/**
 * Rules base class
 * Created by Rahul on 30/05/17.
 */

abstract class Rule {

    /** list of all possibles IDs for a rule*/
    private final int[] ruleIds;

    /** selected ID */
    private int selectedId = 0;

    /**
     * Create a rule with all possible IDs
     * @param ids possible rule array
     */
    Rule(int[] ids) {
        ruleIds = ids;
        selectedId = ids[0];
    }

    /**
     * Get current ID
     * @return selected rule value
     */
    int getSelectedId(){
        return selectedId;
    }

    /**
     * set a value for ID
     * @param id rule value
     */
    void setId(int id){
        selectedId = id;
    }

    public int[] getRuleIds() {
        return ruleIds;
    }
}
