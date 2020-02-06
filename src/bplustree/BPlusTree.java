package bplustree;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 实现B+树
 *
 * @param <T> 指定值类型
 * @param <V> 使用泛型，指定索引类型,并且指定必须继承Comparable
 */
public class BPlusTree <T ,V extends Comparable<V>> implements Serializable {
    //索引数
    private Integer bTreeOrder;
    private Integer maxNumber;
    //根节点
    private Node<T,V> root;
    //叶子节点
    private LeafNode<T,V> left;

    public BPlusTree(){
        this(3);
    }

    public BPlusTree(Integer bTreeOrder){
        this.bTreeOrder=bTreeOrder;
        this.maxNumber=bTreeOrder+1;
        //最开始的根节点是叶子节点
        this.root=new LeafNode<T,V>();
        this.left=null;
    }

    public T find(V key){
        return this.root.find(key);
    }

    public Object[] findAll(){
        return this.root.findAll();
    }

    public void insert(T value,V key){
        if(key == null)
            return;
        //索引非空进行插入操作
        Node<T,V> t=this.root.insert(value,key);
        if (t != null)
            this.root=t;    //?
        //插入后产生叶子节点
        this.left=(LeafNode<T,V>)this.root.refreshLeft();
    }



    //节点公共父类
    abstract class Node<T,V extends Comparable<V>> implements Serializable{
        //父节点
        protected Node<T,V> parent;
        //子节点
        protected Node<T,V>[] childs;
        //键（子节点）数量
        protected Integer number;
        //键
        protected Object[] keys;

        public Node(){
            this.keys=new Object[maxNumber];
            this.childs=new Node[maxNumber];
            this.number=0;
            this.parent=null;
        }

        abstract T find(V key);
        abstract Object[] findAll();
        abstract Node<T, V> insert(T value,V key);
        abstract LeafNode<T, V> refreshLeft();
    }

    //内部节点
    class BPlusNode<T,V extends Comparable<V>> extends Node<T,V>{

        public BPlusNode(){
            super();
        }

        @Override
        T find(V key) {
            //找到具体的子节点，i表示内部节点指向字节点的索引号，确定具体字节点
            int i =0;
            while(i<this.number){
                if(key.compareTo((V)this.keys[i])<=0)
                    break;
                i++;
            }
            if(this.number==i)
                return null;
            //调用确定子节点的方法返回查找的对象
            return this.childs[i].find(key);
        }

        @Override
        Object[] findAll() {
            Object []objects = null;
            int l = 0;
            for(int i = 0; i < childs.length ;i++){
                for(int j = 0; j < childs[i].findAll().length ;j++){
                    objects[l]=childs[i].findAll()[j];
                    l++;
                }
            }
            return objects;
        }


        @Override
        Node<T, V> insert(T value, V key) {
            int i =0;
            while(i<this.number){
                if(key.compareTo((V)this.keys[i])<0)
                    break;
                i++;
            }
            if(key.compareTo((V)this.keys[this.number-1])>=0){
                i--;
            }
            return this.childs[i].insert(value, key);
        }

        @Override
        LeafNode<T, V> refreshLeft() {
            return this.childs[0].refreshLeft();
        }

        Node<T,V> insertNode(Node<T,V> node1,Node<T,V> node2,V key){
            V oldKey=null;//???
            if(this.number>0)
                oldKey=(V)this.keys[this.number-1];
            //如果原有key为null,说明这个非节点是空的,直接放入两个节点即可
            if(key==null||this.number<=0){
                this.keys[0]=node1.keys[node1.number-1];
                this.keys[1]=node2.keys[node2.number-1];
                this.childs[0]=node1;
                this.childs[1]=node2;
                this.number +=2;
                return this;
            }
            //原有节点不为空,则应该先寻找原有节点的位置,然后将新的节点插入到原有节点中
            int i = 0;
            while(key.compareTo((V)this.keys[i]) != 0){
                i++;
            }
            //左边节点的最大值可以直接插入,右边的要挪一挪再进行插入
            this.keys[i]=node1.keys[node1.number-1];
            this.childs[i]=node1;
            Object []tempKeys=new Object[maxNumber];
            Object []tempChilds=new Node[maxNumber];
            System.arraycopy(this.keys,0,tempKeys,0,i+1);
            System.arraycopy(this.childs,0,tempChilds,0,i+1);
            System.arraycopy(this.keys, i + 1, tempKeys, i + 2, this.number - i - 1);
            System.arraycopy(this.childs, i + 1, tempChilds, i + 2, this.number - i - 1);
            tempKeys[i+1]=node2.keys[node2.number-1];
            tempChilds[i+1]=node2;
            this.number++;
            //判断是否需要拆分
            //如果不需要拆分,把数组复制回去,直接返回
            if(this.number<bTreeOrder){
                System.arraycopy(tempKeys, 0, this.keys, 0, this.number);
                System.arraycopy(tempChilds, 0, this.childs, 0, this.number);
                return null;
            }
            Integer middle=this.number/2;
            BPlusNode<T,V> tempNode=new BPlusNode<T,V>();
            tempNode.number=this.number-middle;
            if(this.parent == null){
                BPlusNode<T, V> tempBPlusNode = new BPlusNode<>();
                tempNode.parent = tempBPlusNode;
                oldKey = null;//???
            }else {
                tempNode.parent=this.parent;
            }
            System.arraycopy(tempKeys, middle, tempNode.keys, 0, tempNode.number);
            System.arraycopy(tempChilds, middle, tempNode.childs, 0, tempNode.number);
            for(int j = 0;j<tempNode.number;j++){
                tempNode.childs[j].parent=tempNode;
            }
            this.number=middle;
            this.keys=new Object[maxNumber];
            this.childs=new Node[maxNumber];
            System.arraycopy(tempKeys,0,this.keys,0,middle);
            System.arraycopy(tempChilds,0,this.childs,0,middle);
            //叶子节点拆分成功后,需要把新生成的节点插入父节点  ???
            BPlusNode<T, V> parentNode = (BPlusNode<T, V>)this.parent;
            return parentNode.insertNode(this, tempNode, oldKey);
        }
    }

    //叶子节点
    private class LeafNode<T,V extends Comparable<V>> extends Node{
        public Object values[];
        protected LeafNode left;
        protected LeafNode right;

        public LeafNode(){
            super();
            this.values = new Object[maxNumber];
            this.left = null;
            this.right = null;
        }

        @Override
        Object find(Comparable key) {
            if(this.number<=0)
                return null;
            Integer left=0;
            Integer right=this.number;
            Integer middle =(left + right)/2;
            while (left<right){
                V middleKey= (V)this.keys[middle];
                if(key.compareTo(middleKey)==0){
                    return (T)this.values[middle];
                }else if(key.compareTo(middleKey) < 0){
                    right = middle;
                }else
                    left=middle;
                middle = (right + left)/2;
            }
            return null;
        }

        @Override
        Object[] findAll() {
            return this.values;
        }


        @Override
        Node insert(Object value, Comparable key) {
            //保存原始存在父节点的key值
            V oldKey = null;
            if(this.number > 0)
                oldKey = (V) this.keys[this.number - 1];
            //先插入数据
            int i = 0;
            while(i < this.number){
                if(key.compareTo((V) this.keys[i]) < 0)
                    break;
                i++;
            }
            //复制数组,完成添加
            Object tempKeys[] = new Object[maxNumber];
            Object tempValues[] = new Object[maxNumber];
            System.arraycopy(this.keys, 0, tempKeys, 0, i);
            System.arraycopy(this.values, 0, tempValues, 0, i);
            System.arraycopy(this.keys, i, tempKeys, i + 1, this.number - i);
            System.arraycopy(this.values, i, tempValues, i + 1, this.number - i);
            tempKeys[i] = key;
            tempValues[i] = value;
            this.number++;
            //判断是否需要拆分
            //如果不需要拆分完成复制后直接返回
            if(this.number <= bTreeOrder){
                System.arraycopy(tempKeys, 0, this.keys, 0, this.number);
                System.arraycopy(tempValues, 0, this.values, 0, this.number);

                //有可能虽然没有节点分裂，但是实际上插入的值大于了原来的最大值，所以所有父节点的边界值都要进行更新
                Node node = this;
                while (node.parent != null){
                    V tempkey = (V)node.keys[node.number - 1];
                    if(tempkey.compareTo((V)node.parent.keys[node.parent.number - 1]) > 0){
                        node.parent.keys[node.parent.number - 1] = tempkey;
                        node = node.parent;
                    }
                    else {
                        break;
                    }
                }
                return null;
            }
            //如果需要拆分,则从中间把节点拆分差不多的两部分
            Integer middle = this.number / 2;

            //新建叶子节点,作为拆分的右半部分
            LeafNode<T, V> tempNode = new LeafNode<T, V>();
            tempNode.number = this.number - middle;
            tempNode.parent = this.parent;
            //如果父节点为空,则新建一个非叶子节点作为父节点,并且让拆分成功的两个叶子节点的指针指向父节点
            if(this.parent == null) {
                BPlusNode<T, V> tempBPlusNode = new BPlusNode<>();
                tempNode.parent = tempBPlusNode;
                this.parent = tempBPlusNode;
                oldKey = null;
            }
            System.arraycopy(tempKeys, middle, tempNode.keys, 0, tempNode.number);
            System.arraycopy(tempValues, middle, tempNode.values, 0, tempNode.number);

            //让原有叶子节点作为拆分的左半部分
            this.number = middle;
            this.keys = new Object[maxNumber];
            this.values = new Object[maxNumber];
            System.arraycopy(tempKeys, 0, this.keys, 0, middle);
            System.arraycopy(tempValues, 0, this.values, 0, middle);

            this.right = tempNode;
            tempNode.left = this;

            //叶子节点拆分成功后,需要把新生成的节点插入父节点
            BPlusNode<T, V> parentNode = (BPlusNode<T, V>)this.parent;
            return parentNode.insertNode(this, tempNode, oldKey);
        }

        @Override
        LeafNode refreshLeft() {
            if(this.number <= 0)
                return null;
            return this;
        }

    }
}
