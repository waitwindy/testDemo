package com.gbicc.test;

public class SortClass {

    public static void main(String[] args) {
        int i,sum=0;
        for (i =0;i<10;++i,sum+=i);
        System.out.println(i);
        System.out.println(sum);
    }

    private static void doSomeThing(int[] a, int start, int end) {
        if(start<end){
            int p = core(a,start,end);
            doSomeThing(a,start,p-1);
            doSomeThing(a,p+1,end);
        }
    }

    private static int core(int[] a, int start, int end) {
        int x=a[end];
        int i = start;
        for (int j = start;j<= end-1;j++){
            if (a[j]>=x){
                swap(a,i,j);
                i++;
            }
        }
        return i;
    }

    private static void swap(int[] a, int i, int j) {
        int tmp = a[i];
        a[i]=a[j];
        a[j]=tmp;
    }


}
