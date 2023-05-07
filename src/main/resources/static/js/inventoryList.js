
let multiDeleteBtn = document.querySelector('#multi-delete-btn');
multiDeleteBtn.disabled = true;

// チェック済みの項目が2個以上なら、削除(※)ボタンを活性にする
document.addEventListener('click', () =>
  multiDeleteBtn.disabled = document.querySelectorAll('.checks:checked').length < 2);

//「削除(※)」ボタンが押下された時の処理
function submitForm() {
    // チェックボックスの要素を取得する
    let el = document.getElementsByClassName("checks");
    // 削除対象のレコードを受け取る配列型の変数
    let selectedRecords = [];

    for (let i = 0; i < el.length; i++) {
        // チェックされていた場合、削除対象のレコードとしてidを配列に追加
        if (el[i].checked) {
            selectedRecords.push(el[i].value);
        }
    }

    // 削除対象レコードの情報を受け取るinput要素を取得
    let inputElement = document.querySelector('#selectedRecords');
    // 削除対象レコードの情報を設定
    inputElement.setAttribute("value", selectedRecords);
    // フォームを送信
    document.querySelector('#delete-form').submit();

}