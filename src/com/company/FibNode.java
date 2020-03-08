package com.company;

import java.util.ArrayList;
import java.util.List;

public class FibNode {
    FibNode parent, left, right;
    List<FibNode> children;
    int key;
    String hashtag;
    Boolean childCut;

    public FibNode(String tag, int value) {
        this.right = this;
        this.left = this;
        this.key = value;
        this.hashtag = tag;

        this.parent = null;
        this.children = new ArrayList<FibNode>();
        this.childCut = false;
    }
}
