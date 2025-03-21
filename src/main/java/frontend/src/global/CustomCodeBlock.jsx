import ShikiHighlighter from "react-shiki";

const CustomCodeBlock = ({code, language}) => {
    return (
        <ShikiHighlighter language={language} theme="material-theme-lighter">
            {code.trim()}
        </ShikiHighlighter>
    );
}

export default CustomCodeBlock
