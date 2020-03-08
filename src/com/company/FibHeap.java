package com.company;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class FibHeap {
    private FibNode root;
    int heapcount = 0;

    public FibHeap(){
        this.root = null;
    }

    public FibNode insert(String tag, int element) {
        FibNode new_element = new FibNode(tag, element);
        if(root != null) {
            connectToHeap(root, new_element);
            if(new_element.key > root.key) {
                root = new_element;
            }
        }
        else {
            root = new_element;
        }
        heapcount++;
        return new_element;
    }

    public void insert(FibNode node){
        if(root != null) {
            connectToHeap(root, node);
            if(node.key > root.key) {
                root = node;
            }
        }
        else {
            root = node;
        }
        heapcount++;
    }

    public void increaseKey(FibNode element, int key) {
        element.key += key;
        if((element.parent == null) || (element.key < element.parent.key)){
            if(element.key > root.key){
                root = element;
            }
        }
        else if(element.parent != null){
            FibNode parent = element.parent;
            removeChildAndParent(element, parent);
            parent.childCut = !parent.childCut;
            if(!parent.childCut)
                cascadeCut(parent);
        }
    }

    public void cascadeCut(FibNode node){
        while (node.parent!=null && node.childCut){
            FibNode parent = node.parent;
            removeChildAndParent(node, node.parent);
            connectToHeap(root, node);
            node.childCut = !node.childCut;
            node = parent;
        }
    }

    public void removeChildAndParent(FibNode child, FibNode parent){
        child.parent = null;
        parent.children.remove(child);
        connectToHeap(root, child);
        heapcount++;
    }

    public List<String> extractMax(int no) {
        List<String> removedList = new LinkedList<>();
        for (int i=0; i<no; i++) {
            if(!isEmpty()) {
                removedList.add(removeMax());
            }
            else{
                i = no;
            }
        }
        return removedList;
    }

    public String removeMax(){
        heapcount--;
        FibNode parent = root;
        if(root.children.size() == 0 && heapcount>1){
            connectNodes(root.left, root.right);
            root = root.right;
        }
        else if(root.children.size() > 0){
            insertChildrenToHeap();
        }

        FibNode currenetNode = root;
        FibNode nextNode = root.right;
        while(currenetNode != nextNode){
            if(nextNode.key > root.key)
                root = nextNode;
            nextNode = nextNode.right;
        }

        if(heapcount > 1)
            checkPairs();

        parent.left = null;
        parent.right = null;
        return parent.hashtag;
    }

    public Boolean isEmpty(){
        return (root == null);
    }

    public void checkPairs(){
        FibNode currentNode = root;
        FibNode nextNode = root.right;
        do{
            while (currentNode != nextNode){
                if(currentNode.children.size() == nextNode.children.size()){
                    currentNode = pairwiseCombine(currentNode, nextNode);
                    nextNode = currentNode;
                }
                nextNode = nextNode.right;
            }
            nextNode = nextNode.right;
        }while(currentNode != nextNode);
    }

//    public void checkPairs(){
//        FibNode currentNode, nextNode;
//        currentNode = root;
//        nextNode = root.right;
//        do{
//            while(currentNode != nextNode){
//                if(currentNode.children.size() == nextNode.children.size()){
//                    currentNode = pairwiseCombine(currentNode, nextNode);
//                    nextNode = currentNode;
//                }
//                nextNode = nextNode.right;
//            }
//            currentNode = currentNode.right;
//            nextNode = currentNode.right;
//        } while(root != currentNode);
//    }
//
    private FibNode pairwiseCombine(FibNode node1, FibNode node2) {
        FibNode minNode, maxNode;
        if(node1.key > node2.key){
            minNode = node2;
            maxNode = node1;
        }
        else{
            minNode = node1;
            maxNode = node2;
        }

        assignNeighbours(minNode, maxNode);
        maxNode.children.add(minNode);
        minNode.parent = maxNode;
        heapcount--;

        return maxNode;
    }

    public void assignNeighbours(FibNode minNode, FibNode maxNode){
        FibNode minLeft = minNode.left;
        FibNode minRight = minNode.right;
        minLeft.right = minRight;
        minRight.left = minLeft;

        int count = maxNode.children.size();
        if(count > 0){
            minNode.left = maxNode.children.get(count-1);
            minNode.right = maxNode.children.get(0);
            maxNode.children.get(count-1).right = minNode;
            maxNode.children.get(0).left = minNode;
        }
        else{
            minNode.left = minNode;
            minNode.right = minNode;
        }
    }

    public void connectToHeap(FibNode node1, FibNode node2){
        node2.right = node1.right;
        node1.right.left = node2;
        node1.right = node2;
        node2.left = node1;
    }

    public void insertChildrenToHeap(){
        FibNode parent = root;
        int childCount = root.children.size();
        FibNode leftMostChild = root.children.get(0);
        FibNode rightMostChild = root.children.get(childCount-1);
        for(int i=0; i<childCount; i++){
            int nextChild = (i+1) % childCount;
            int beforeChild = (childCount+i-1) % childCount;
            root.children.get(i).parent = null;

            connectNodes(root.children.get(i), root.children.get(nextChild));
            connectNodes(root.children.get(beforeChild), root.children.get(i));
        }
        if(heapcount > 1){
            connectNodes(root.left, root.children.get(0));
            connectNodes(root.children.get(childCount-1), root.right);
        }
        root = parent.children.get(0);
        parent.children.clear();
        heapcount += childCount;
    }

    public void connectNodes(FibNode leftNode, FibNode rightNode){
        leftNode.right = rightNode;
        rightNode.left = leftNode;
    }

}
