package dungeonmania.entities;

import com.google.gson.JsonObject;

public enum Logic {
    ANY("any"),
    AND("and"),
    OR("or"),
    XOR("xor"),
    NOT("not"),
    CO_AND("co_and");
    

    private final String logic;
    private Logic(String logic) {
        this.logic = logic;
    }

    public static Logic getLogic(String logic) {
        for (Logic l : Logic.values()) { 
            if (logic.equals(l.toString())) {
                return l;
            }
        }
        return null;
    }

    public static Logic getLogicFromJsonObject(JsonObject jsonObject) {
        try {
            return Logic.getLogic(jsonObject.get("logic").toString().replace("\"", ""));
        } catch (NullPointerException e) {
            return ANY;
        }
    }

    @Override
    public String toString() {
        return this.logic.toLowerCase();
    }
}
