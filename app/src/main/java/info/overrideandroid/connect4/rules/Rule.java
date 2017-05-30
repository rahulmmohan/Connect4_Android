package info.overrideandroid.connect4.rules;

/**
 * Rules base class
 * Created by Rahul on 30/05/17.
 */

public abstract class Rule {

    /** list of all possibles IDs for a rule*/
    public final int[] ruleIds;

    /** selected ID */
    private int selectedId = 0;

    /**
     * Create a rule with all possible IDs
     * @param ids
     */
    Rule(int[] ids) {
        ruleIds = ids;
        selectedId = ids[0];
    }

    /**
     * Get current ID
     * @return
     */
    int getSelectedId(){
        return selectedId;
    }

    /**
     * set a value for ID
     * @param id
     */
    void setId(int id){
        selectedId = id;
    }
}
