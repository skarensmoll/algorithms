'use strict';

process.stdin.resume();
process.stdin.setEncoding('utf-8');

let inputString = '';
let currentLine = 0;

process.stdin.on('data', function(inputStdin) {
    inputString += inputStdin;
});

process.stdin.on('end', function() {
    inputString = inputString.split('\n');

    main();
});

function readLine() {
    return inputString[currentLine++];
}


function dasher(arr) {
    let half = Math.floor(arr.length / 2);
    arr = arr.map((item, i) => {
        if(i < half) {
            item[1] = '-';
        }
    });
}

function merge(arr, lo, h, hi) {
    let lowHalf = arr.slice(lo, h+1);
    let highHalf = arr.slice(h+1, hi+1);
    let i = lo;
    let j = 0;
    let k = 0;
    while(j < lowHalf.length && k < highHalf.length) {
       if(lowHalf[j][0] <= highHalf[k][0]) {
           arr[i] = lowHalf[j];
           j++;
       } else {
           arr[i] = highHalf[k];
           k++;
       }
       i++;
    }

    while(j < lowHalf.length) {
        arr[i] = lowHalf[j];
        j++;
        i++;
    }

    while(k < highHalf.length) {
        arr[i] = highHalf[k];
        k++;
        i++;
    }
}


function mergeSort(arr, lo,  hi) {
    if (lo < hi) {
        let half = Math.floor((lo + hi) / 2);
        mergeSort(arr, lo, half);
        mergeSort(arr, half + 1, hi);
        merge(arr,lo, half, hi);
    }
}


function countSort(arr) {
    dasher(arr);
    mergeSort(arr, 0, arr.length - 1)
}


function main() {
    const n = parseInt(readLine().trim(), 10);

    let arr = new Array(n);

    for (let i = 0; i < n; i++) {
        var [num, text ] = readLine().split(' ')
        arr[i] = [Number(num), text]
    }

    countSort(arr);
    var letters = arr.map((a) => a[1]).join(' ');
    console.log(letters);
}
