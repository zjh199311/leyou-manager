import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author river
 * @title: Test
 * @projectName leyou
 * @description: TODO
 * @date 2019/9/417:16
 */
public class Test {
   /* public static void main(String[] args) {
        List list = new ArrayList();
        // 增加：add() 将指定对象存储到容器中
        list.add("计算机网络");
        list.add("现代操作系统");
        list.add("java编程思想");
        list.add("java核心技术");
        list.add("java语言程序设计");
        System.out.println(list);
        // add,在0角标位置添加一本书
        list.add(0, "舒克和贝塔");
        System.out.println(list);
        // 在list2集合的1角标位置添加list集合元素
        List list2 = new ArrayList();
        list2.add("史记");
        list2.add("资治通鉴");
        list2.add("全球通史");
        boolean addAll = list2.addAll(1, list);
        *//*list2.remove(0);
        list2.set(0,"it");*//*
        System.out.println(list2);
        System.out.println(addAll); //true		 System.out.println(list2);
    }*/



    /*public static void main(String[] args) {
        LinkedList list = new LinkedList();
        list.add("西游记");
        list.add("三国演义");
        list.add("石头记");
        list.add("水浒传");
        list.add("全球通史");
        list.addFirst("史记");
        list.addLast("呐喊");
        // list.addFirst(null);
        // list.addLast(null);
        System.out.println(list);
        // 获取指定位置处的元素。
        String str = (String) list.get(0);
        System.out.println(str);
        // 返回此列表的第一个元素。
        String str2 = (String) list.getFirst();
        System.out.println(str2);
        System.out.println(str.equals(str2));

        // 获取指定位置处的元素。
        String str3 = (String) list.get(list.size() - 1);
        // 返回此列表的最后一个元素。
        String str4 = (String) list.getLast();
        System.out.println(str3.equals(str4));

        // 获取但不移除此列表的头（第一个元素）。
        Object element = list.element();
        System.out.println(element);

        int size = list.size();
        System.out.println(size);
    }*/


  /*  public static void main(String[] args) {
        LinkedList list = new LinkedList();
        list.add("aa");
        list.add("bb");
        list.add("cc");
        Iterator dit = list.descendingIterator();
        while (dit.hasNext()) {
            System.out.println(dit.next());
        }
    }*/


    public static void main(String[] args) {
        LinkedList list = new LinkedList();
        // 压栈，先进后出
        list.push("西游记");
        list.push("三国演义");
        list.push("石头记");
        list.push("水浒传");
        System.out.println(list);
        // 弹栈
        String str1 = (String) list.pop();
        System.out.println(str1);
        String str2 = (String) list.pop();
        System.out.println(str2);
        String str3 = (String) list.pop();
        System.out.println(str3);
        String str4 = (String) list.pop();
        System.out.println(str4);
        System.out.println(list.size());// 0
        System.out.println(list); //[]
    }



}
