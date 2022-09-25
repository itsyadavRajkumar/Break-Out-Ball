/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BreakOutBall;

/**
 *
 * @author Rajkumar Yadav
 */
public class Test {
    public static void main(String[] args) {
        int arr[] = {1,2,3,4,5};
        int i=0,j=arr.length-1;
        while(i<j){
            int temp=arr[i];
            arr[i]=arr[j];
            arr[j]=temp;
            i++;
            j++;
        }
        for(int k=0; k<arr.length-1; k++){
            System.out.println(arr[k]);
        }
    }
}