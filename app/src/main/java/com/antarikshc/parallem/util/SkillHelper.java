package com.antarikshc.parallem.util;

import com.antarikshc.parallem.models.Skill;

public class SkillHelper {

    // What am about to do is very stupid
    // But am on a time constraint and implementing Custom AutoCompleteTextView and CustomAdapter
    // is not doable right at the moment

    public static Integer getSkillId(Skill[] skills, String inputSkill) {

        Integer skillId = null;

        for (Skill skill : skills) {

            if (inputSkill.equals(skill.getName())) {
                skillId = skill.getSkillId();
            }

        }

        return skillId;
    }

    public static String[] getSkillNameArray(Skill[] skills) {

        String[] skillNameArray = new String[skills.length];

        for (int i = 0; i < skills.length; i++) {
            skillNameArray[i] = skills[i].getName();
        }

        return skillNameArray;
    }

}
