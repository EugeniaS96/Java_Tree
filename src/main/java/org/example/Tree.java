package org.example;

import javax.swing.tree.TreeNode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Tree {
    public static void main(String[] args) {
        /**
         * <html>
         *     <head><title></title></head>
         *     <body>
         *         <div>
         *             <p></p>
         *         </div>
         *         <h1></h1>
         *     </body>
         * </html>
         */
        TreeNode rootNode = new TreeNode("html");
        TreeNode headNode = new TreeNode("head");
        TreeNode bodyNode = new TreeNode("body");
        TreeNode titleNode = new TreeNode("title");
        TreeNode divNode = new TreeNode("div");
        TreeNode pNode = new TreeNode("p");
        TreeNode h1Node = new TreeNode("h1");

        rootNode.addChild(headNode);
        rootNode.addChild(bodyNode);
        headNode.addChild(titleNode);
        bodyNode.addChild(divNode);
        bodyNode.addChild(h1Node);
        divNode.addChild(pNode);

        Tree tree = new Tree(rootNode);
        System.out.println(tree);
        System.out.println("=======================");

        TreeNode foundNode = tree.findNodeByValue("body");
        System.out.println(foundNode);
        System.out.println("=======================");

        boolean result = tree.deleteNode("div");
        System.out.println(result);
        System.out.println(tree);
    }

    TreeNode root;
    // Конструктор класса Tree
    public Tree(TreeNode root) {
        this.root = root;
    }

    @Override
    public String toString() {
        return root.toString();
    }

    public static class TreeNode {
        private String value;
        private List<TreeNode> children;

        public TreeNode(String value) {
            this.value = value;
            this.children = new ArrayList<>();
        }

        public void addChild(TreeNode child) {
            children.add(child);
        }

        public List<TreeNode> getChildren() {
            return children;
        }

        public String getValue() {
            return value;
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            return super.equals(obj);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            if (children != null && !children.isEmpty()) {
                for (TreeNode treeNode : children) {
                    sb.append(treeNode.toString());
                }
            }
            return "\n<" + value + ">" +
                    "\n" +sb.toString() +
                    "\n" + value + ">";
        }
    }

    public TreeNode findNodeByValue(String value) {
        TreeNode currentNode = root;
        if (!currentNode.getValue().equals(value)) {
            for (TreeNode child : currentNode.getChildren()) {
                TreeNode nodeByValue = findNodeByValue(child, value);
                if (nodeByValue != null) {
                    return nodeByValue;
                }
            }
            return null;
        }
        return currentNode;
    }

    public TreeNode findNodeByValue(TreeNode currentNode, String value) {
        if (!currentNode.getValue().equals(value)) {
            if (currentNode.getChildren() == null || currentNode.getChildren().isEmpty()) {
                return null;
            }
            for (TreeNode child : currentNode.getChildren()) {
                TreeNode nodeByValue = findNodeByValue(child, value);
                if (nodeByValue != null) {
                    return nodeByValue;
                }
            }
            return null;
        }
        return currentNode;
    }

    public boolean deleteNode(String value) {
        if (root.getChildren() == null || root.getChildren().isEmpty()) {
            return false;
        }
        Iterator<TreeNode> iterator = root.getChildren().iterator();
        while (iterator.hasNext()) {
            TreeNode child = iterator.next();
            boolean nodeByValue = deleteNode(child, value);
            if (nodeByValue) {
                if (child.getValue().equals(value)) {
                    root.getChildren().remove(child);
                }
                return true;
            }
        }
        return false;
    }

    public boolean deleteNode(TreeNode currentNode, String value) {
        if (!currentNode.getValue().equals(value)) {
            if (currentNode.getChildren() == null || currentNode.getChildren().isEmpty()) {
                return false;
            }
            Iterator<TreeNode> iterator = currentNode.getChildren().iterator();
            while (iterator.hasNext()) {
                TreeNode child = iterator.next();
                boolean nodeByValue = deleteNode(child, value);
                if (nodeByValue) {
                    if (child.getValue().equals(value)) {
                        currentNode.getChildren().remove(child);
                    }
                    return true;
                }
            }
            return false;
        }
        return true;
    }
}

