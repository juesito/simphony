/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.simphony.selectors;

import com.simphony.pojos.ComboBox;
import java.io.Serializable;
import java.text.Collator;
import java.util.Comparator;

/**
 *
 * @author ga25rciaj
 */
public class ComboBoxComparator implements Comparator<ComboBox>, Serializable {

    private static final long serialVersionUUID = 1L;
    private Comparator comparator;

    ComboBoxComparator() {
        comparator = Collator.getInstance();
    }

    public int compare(ComboBox o1, ComboBox o2) {
        return comparator.compare(o1.getName(), o2.getName());
    }

}
