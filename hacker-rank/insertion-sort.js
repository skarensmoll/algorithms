function insertionSort(arr) {
  for (let i = 1; i < arr.length; i++) {
    debugger;
    if (arr[i] < arr[i - 1]) {
      rearrange(arr, i);
    }
  }
}

function rearrange(arr, i) {
  let value = arr[i];
  while (value < arr[i - 1] && i > 0) {
    arr[i] = arr[i - 1];
    i--;
  }
  arr[i] = value;
}

var arr = [3, 4, 7, 5, 6, 2, 1];
insertionSort(arr);
console.log(arr);

