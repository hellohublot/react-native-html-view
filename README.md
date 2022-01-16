- 支持 html 富文本渲染，支持图片，给定固定宽度，能够自动适应高度

## Usage

[点击查看完整示例 Example](./example/App.js)

```bash
yarn add 'https://github.com/hellohublot/react-native-html-view.git'
```

```javascript
import HTHtmlView from 'react-native-html-view'

<HTHtmlView 
	style={styleList.htmlView}
	value={`
		<div>
			<img width=150pt src="https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fpng.pngtree.com%2Felement_our%2Fsm%2F20180524%2Fsm_5b07290f80d15.png&refer=http%3A%2F%2Fpng.pngtree.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1644904468&t=faede76158551639e8426224a688d90a" />
		</div>
		<span>
			<font color="white" size=3>你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊你好啊</font>
		</span>
	`}
	maxTextSize={{ width: 200, height: 0 }}
/>
```

## Author

hellohublot, hublot@aliyun.com
