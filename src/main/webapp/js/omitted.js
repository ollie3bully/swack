let contents = document.querySelectorAll('.list-name');

contents.forEach(content => {
  content.textContent = omittedContent(content.textContent);
});

function omittedContent(string) {
  // 定数で宣言
  const MAX_LENGTH = 16

  // もしstringの文字数がMAX_LENGTH（今回は10）より大きかったら末尾に...を付け足して返す。
  if (string.length > MAX_LENGTH) {

    // substr(何文字目からスタートするか, 最大値);
    return string.substr(0, MAX_LENGTH) + '...';
  }
  //　文字数がオーバーしていなければそのまま返す
  return string;
}