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
        // Создаем узлы дерева, соответствующие HTML-тегам
        TreeNode rootNode = new TreeNode("html");
        TreeNode headNode = new TreeNode("head");
        TreeNode bodyNode = new TreeNode("body");
        TreeNode titleNode = new TreeNode("title");
        TreeNode divNode = new TreeNode("div");
        TreeNode pNode = new TreeNode("p");
        TreeNode h1Node = new TreeNode("h1");

        // Добавляем дочерние узлы к родительским узлам
        rootNode.addChild(headNode);
        rootNode.addChild(bodyNode);
        headNode.addChild(titleNode);
        bodyNode.addChild(divNode);
        bodyNode.addChild(h1Node);
        divNode.addChild(pNode);

        // Создаем дерево с корневым узлом
        Tree tree = new Tree(rootNode);
        System.out.println(tree);
        System.out.println("=======================");

        // Ищем узел с тегом "body" и выводим его
        TreeNode foundNode = tree.findNodeByValue("body");
        System.out.println(foundNode);
        System.out.println("=======================");

        // Удаляем узел с тегом "div" и выводим результат
        boolean result = tree.deleteNode("div");
        System.out.println(result);
        System.out.println(tree);
    }
    // Корневой узел дерева
    TreeNode root;
    // Конструктор класса Tree
    public Tree(TreeNode root) {
        this.root = root;
    }
    // Переопределение метода toString для вывода дерева
    @Override
    public String toString() {
        return root.toString();
    }

    // Вложенный класс TreeNode, представляющий узел дерева
    public static class TreeNode {
        //значение узла дерева - в нашем случае строка (название тэга)
        private String value;
        // Список дочерних узлов
        private List<TreeNode> children;

        // Конструктор для создания узла с заданным значением
        public TreeNode(String value) {
            this.value = value;
            this.children = new ArrayList<>();
        }

        // Метод для добавления дочернего узла
        public void addChild(TreeNode child) {
            children.add(child);
        }

        // Метод для получения списка дочерних узлов
        public List<TreeNode> getChildren() {
            return children;
        }

        //получение значения узла дерева
        public String getValue() {
            return value;
        }

        // Переопределение метода hashCode (не реализовано)
        @Override
        public int hashCode() {
            return super.hashCode();
        }

        // Переопределение метода equals (не реализовано)
        @Override
        public boolean equals(Object obj) {
            return super.equals(obj);
        }

        @Override
        // Переопределение метода toString для вывода узла и его дочерних узлов
        //Чтобы дерево отображалось в красивом виде
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

    //нахождение узла по значению
    public TreeNode findNodeByValue(String value) {
        TreeNode currentNode = root;
        //рекурсивно проходимся по всему дереву от корневого узла и по всем дочерним
        //и проверяем, не соответсвует ли просматриваемое значение тому, которое нужно найти
        //если да, возвращаем текущий узел, если нет возвр null
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

    /**
     * Вспомогательный метод для поиска узла по значению.
     * @param currentNode Текущий узел, с которого начинается поиск.
     * @param value Значение, которое нужно найти.
     * @return Найденный узел или null, если узел не найден.
     */
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

    //удаление узла с определенным значением. Начинаем с корневого узла
    //Проверяем есть ли у его дочерних эл нужное нам значение
    //если нашли удаляем. @return true, если узел был удален, иначе false.
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


    //если у дочерних эл корневого узла значение, кот мы хотим удалить нет
    //проходимся по дочерним узлам дочерних узлов
    /**
     * Вспомогательный метод для удаления узла с заданным значением.
     * @param currentNode Текущий узел, с которого начинается поиск.
     * @param value Значение узла, который нужно удалить.
     * @return true, если узел был удален, иначе false.
     */
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

