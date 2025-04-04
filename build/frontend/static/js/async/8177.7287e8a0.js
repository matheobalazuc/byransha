"use strict";(self.webpackChunklogin_page=self.webpackChunklogin_page||[]).push([["8177"],{74461:function(e,t,n){n.r(t),n.d(t,{default:()=>d});var a=n(47714),r=n(22700),s=n(70288),i=n(71074),c=n(97488),l=n(51299);let u=Object.freeze(JSON.parse('{"displayName":"Crystal","fileTypes":["cr"],"firstLineMatch":"^#!/.*\\\\bcrystal","foldingStartMarker":"^(\\\\s*+(annotation|module|class|struct|union|enum|def(?!.*\\\\bend\\\\s*$)|unless|if|case|begin|for|while|until|^=begin|(\\"(\\\\\\\\.|[^\\"])*+\\"|\'(\\\\\\\\.|[^\'])*+\'|[^#\\"\'])*(\\\\s(do|begin|case)|(?<!\\\\$)[\\\\-+=\\\\&|*/~%\\\\^<>]\\\\s*+(if|unless)))\\\\b(?![^;]*+;.*?\\\\bend\\\\b)|(\\"(\\\\\\\\.|[^\\"])*+\\"|\'(\\\\\\\\.|[^\'])*+\'|[^#\\"\'])*(\\\\{(?![^}]*+})|\\\\[(?![^\\\\]]*+]))).*$|#.*?\\\\(fold\\\\)\\\\s*+$","foldingStopMarker":"((^|;)\\\\s*+end\\\\s*+(#.*)?$|(^|;)\\\\s*+end\\\\..*$|^\\\\s*+[}\\\\]],?\\\\s*+(#.*)?$|#.*?\\\\(end\\\\)\\\\s*+$|^=end)","name":"crystal","patterns":[{"captures":{"1":{"name":"keyword.control.class.crystal"},"2":{"name":"keyword.control.class.crystal"},"3":{"name":"entity.name.type.class.crystal"},"5":{"name":"punctuation.separator.crystal"},"6":{"name":"support.class.other.type-param.crystal"},"7":{"name":"entity.other.inherited-class.crystal"},"8":{"name":"punctuation.separator.crystal"},"9":{"name":"punctuation.separator.crystal"},"10":{"name":"support.class.other.type-param.crystal"},"11":{"name":"punctuation.definition.variable.crystal"}},"match":"^\\\\s*(abstract)?\\\\s*(class|struct|union|annotation|enum)\\\\s+(([.A-Z_:\\\\x{80}-\\\\x{10FFFF}][.\\\\w:\\\\x{80}-\\\\x{10FFFF}]*(\\\\(([,\\\\s.a-zA-Z0-9_:\\\\x{80}-\\\\x{10FFFF}]+)\\\\))?(\\\\s*(<)\\\\s*[.:A-Z\\\\x{80}-\\\\x{10FFFF}][.:\\\\w\\\\x{80}-\\\\x{10FFFF}]*(\\\\(([.a-zA-Z0-9_:]+\\\\s,)\\\\))?)?)|((<<)\\\\s*[.A-Z0-9_:\\\\x{80}-\\\\x{10FFFF}]+))","name":"meta.class.crystal"},{"captures":{"1":{"name":"keyword.control.module.crystal"},"2":{"name":"entity.name.type.module.crystal"},"3":{"name":"entity.other.inherited-class.module.first.crystal"},"4":{"name":"punctuation.separator.inheritance.crystal"},"5":{"name":"entity.other.inherited-class.module.second.crystal"},"6":{"name":"punctuation.separator.inheritance.crystal"},"7":{"name":"entity.other.inherited-class.module.third.crystal"},"8":{"name":"punctuation.separator.inheritance.crystal"}},"match":"^\\\\s*(module)\\\\s+(([A-Z\\\\x{80}-\\\\x{10FFFF}][\\\\w\\\\x{80}-\\\\x{10FFFF}]*(::))?([A-Z\\\\x{80}-\\\\x{10FFFF}][\\\\w\\\\x{80}-\\\\x{10FFFF}]*(::))?([A-Z\\\\x{80}-\\\\x{10FFFF}][\\\\w\\\\x{80}-\\\\x{10FFFF}]*(::))*[A-Z\\\\x{80}-\\\\x{10FFFF}][\\\\w\\\\x{80}-\\\\x{10FFFF}]*)","name":"meta.module.crystal"},{"captures":{"1":{"name":"keyword.control.lib.crystal"},"2":{"name":"entity.name.type.lib.crystal"},"3":{"name":"entity.other.inherited-class.lib.first.crystal"},"4":{"name":"punctuation.separator.inheritance.crystal"},"5":{"name":"entity.other.inherited-class.lib.second.crystal"},"6":{"name":"punctuation.separator.inheritance.crystal"},"7":{"name":"entity.other.inherited-class.lib.third.crystal"},"8":{"name":"punctuation.separator.inheritance.crystal"}},"match":"^\\\\s*(lib)\\\\s+(([A-Z]\\\\w*(::))?([A-Z]\\\\w*(::))?([A-Z]\\\\w*(::))*[A-Z]\\\\w*)","name":"meta.lib.crystal"},{"captures":{"1":{"name":"keyword.control.lib.type.crystal"},"2":{"name":"entity.name.lib.type.crystal"},"3":{"name":"keyword.control.lib.crystal"},"4":{"name":"entity.name.lib.type.value.crystal"}},"match":"(?<!\\\\.)\\\\b(type)\\\\s+([A-Z]\\\\w+)\\\\s*(=)\\\\s*(.+)","name":"meta.lib.type.crystal"},{"match":"(?<!\\\\.)\\\\b(fun|begin|case|class|else|elsif|end|ensure|enum|for|if|macro|module|rescue|struct|then|union|unless|until|when|while)\\\\b(?![?!:])","name":"keyword.control.crystal"},{"match":"(?<!\\\\.)\\\\b(abstract|alias|asm|break|extend|in|include|next|of|private|protected|struct|return|select|super|with|yield)\\\\b(?![?!:])","name":"keyword.control.primary.crystal"},{"match":"(?<!\\\\.)\\\\b(describe|context|it|expect_raises)\\\\b(?![?!:])","name":"keyword.control.crystal"},{"match":"(?<!\\\\.)\\\\bdo\\\\b\\\\s*","name":"keyword.control.start-block.crystal"},{"match":"(?<=\\\\{)(\\\\s+)","name":"meta.syntax.crystal.start-block"},{"match":"(?<!\\\\.)\\\\b(pointerof|typeof|sizeof|instance_sizeof|offsetof|previous_def|forall|out|uninitialized)\\\\b(?![?!:])|\\\\.(is_a\\\\?|nil\\\\?|responds_to\\\\?|as\\\\?|as\\\\x08)","name":"keyword.control.pseudo-method.crystal"},{"match":"\\\\bnil\\\\b(?![?!:])","name":"constant.language.nil.crystal"},{"match":"\\\\b(true|false)\\\\b(?![?!:])","name":"constant.language.boolean.crystal"},{"match":"\\\\b(__(DIR|FILE|LINE|END_LINE)__)\\\\b(?![?!:])","name":"variable.language.crystal"},{"match":"\\\\b(self)\\\\b(?![?!:])","name":"variable.language.self.crystal"},{"match":"(?<!\\\\.)\\\\b(((class_)?((getter|property)\\\\b[!?]?|setter\\\\b))|(def_(clone|equals|equals_and_hash|hash)|delegate|forward_missing_to)\\\\b)(?![?!:])","name":"support.function.kernel.crystal"},{"begin":"\\\\b(require)\\\\b","captures":{"1":{"name":"keyword.other.special-method.crystal"}},"end":"$|(?=#)","name":"meta.require.crystal","patterns":[{"include":"$self"}]},{"captures":{"1":{"name":"punctuation.definition.variable.crystal"}},"match":"(@)[a-zA-Z_\\\\x{80}-\\\\x{10FFFF}][\\\\w\\\\x{80}-\\\\x{10FFFF}]*[?!=]?","name":"variable.other.readwrite.instance.crystal"},{"captures":{"1":{"name":"punctuation.definition.variable.crystal"}},"match":"(@@)[a-zA-Z_\\\\x{80}-\\\\x{10FFFF}][\\\\w\\\\x{80}-\\\\x{10FFFF}]*[?!=]?","name":"variable.other.readwrite.class.crystal"},{"captures":{"1":{"name":"punctuation.definition.variable.crystal"}},"match":"(\\\\$)[a-zA-Z_]\\\\w*","name":"variable.other.readwrite.global.crystal"},{"captures":{"1":{"name":"punctuation.definition.variable.crystal"}},"match":"(?!%[Qxrqwi]?[(\\\\[{<|])%([a-zA-Z_]\\\\w*\\\\.)*[a-zA-Z_]\\\\w*","name":"variable.other.readwrite.fresh.crystal"},{"captures":{"1":{"name":"punctuation.definition.variable.crystal"}},"match":"(\\\\$)([!@\\\\&`\'+]|\\\\d+|[~=/\\\\\\\\,;.<>_*$?:\\"]|-[0adFiIlpv])","name":"variable.other.readwrite.global.pre-defined.crystal"},{"begin":"\\\\b(ENV)\\\\[","beginCaptures":{"1":{"name":"variable.other.constant.crystal"}},"end":"]","name":"meta.environment-variable.crystal","patterns":[{"include":"$self"}]},{"match":"\\\\b[A-Z\\\\x{80}-\\\\x{10FFFF}][\\\\w\\\\x{80}-\\\\x{10FFFF}]*","name":"support.class.crystal"},{"match":"(?<!\\\\.)\\\\b(abort|at_exit|caller|exit|gets|loop|main|p|pp|print|printf|puts|raise|rand|read_line|sleep|spawn|sprintf|system|debugger|record|spawn)\\\\b(?![?!:])","name":"support.function.kernel.crystal"},{"match":"\\\\b[_A-Z]+\\\\b","name":"variable.other.constant.crystal"},{"begin":"(?=def\\\\b)(?<=^|\\\\s)(def)\\\\s+((?>[a-zA-Z_]\\\\w*(?>\\\\.|::))?(?>[a-zA-Z_]\\\\w*(?>[?!]|=(?!>))?|\\\\^|===?|!=|>[>=]?|<=>|<[<=]?|[%\\\\&`/|]|\\\\*\\\\*?|=?~|[\\\\-+]@?|\\\\[][?=]?|\\\\[]=?))\\\\s*(\\\\()","beginCaptures":{"1":{"name":"keyword.control.def.crystal"},"2":{"name":"entity.name.function.crystal"},"3":{"name":"punctuation.definition.parameters.crystal"}},"end":"\\\\)","endCaptures":{"0":{"name":"punctuation.definition.parameters.crystal"}},"name":"meta.function.method.with-arguments.crystal","patterns":[{"begin":"(?![\\\\s,)])","end":"(?=,|\\\\)\\\\s*)","patterns":[{"captures":{"1":{"name":"storage.type.variable.crystal"},"2":{"name":"constant.other.symbol.hashkey.parameter.function.crystal"},"3":{"name":"punctuation.definition.constant.hashkey.crystal"},"4":{"name":"variable.parameter.function.crystal"}},"match":"\\\\G([\\\\&*]?)(?:([_a-zA-Z]\\\\w*(:))|([_a-zA-Z]\\\\w*))"},{"include":"$self"}]}]},{"captures":{"1":{"name":"keyword.control.def.crystal"},"3":{"name":"entity.name.function.crystal"}},"match":"(?=def\\\\b)(?<=^|\\\\s)(def)\\\\b(\\\\s+((?>[a-zA-Z_]\\\\w*(?>\\\\.|::))?(?>[a-zA-Z_]\\\\w*(?>[?!]|=(?!>))?|\\\\^|===?|!=|>[>=]?|<=>|<[<=]?|[%\\\\&`/|]|\\\\*\\\\*?|=?~|[\\\\-+]@?|\\\\[][?=]?|\\\\[]=?)))?","name":"meta.function.method.without-arguments.crystal"},{"match":"\\\\b[0-9][0-9_]*\\\\.[0-9][0-9_]*([eE][+\\\\-]?[0-9_]+)?(f(?:32|64))?\\\\b","name":"constant.numeric.float.crystal"},{"match":"\\\\b[0-9][0-9_]*(\\\\.[0-9][0-9_]*)?[eE][+\\\\-]?[0-9_]+(f(?:32|64))?\\\\b","name":"constant.numeric.float.crystal"},{"match":"\\\\b[0-9][0-9_]*(\\\\.[0-9][0-9_]*)?([eE][+\\\\-]?[0-9_]+)?(f(?:32|64))\\\\b","name":"constant.numeric.float.crystal"},{"match":"\\\\b(?!0[0-9])[0-9][0-9_]*([ui](8|16|32|64|128))?\\\\b","name":"constant.numeric.integer.decimal.crystal"},{"match":"\\\\b0x[_\\\\h]+([ui](8|16|32|64|128))?\\\\b","name":"constant.numeric.integer.hexadecimal.crystal"},{"match":"\\\\b0o[0-7_]+([ui](8|16|32|64|128))?\\\\b","name":"constant.numeric.integer.octal.crystal"},{"match":"\\\\b0b[01_]+([ui](8|16|32|64|128))?\\\\b","name":"constant.numeric.integer.binary.crystal"},{"begin":":\'","beginCaptures":{"0":{"name":"punctuation.definition.symbol.begin.crystal"}},"end":"\'","endCaptures":{"0":{"name":"punctuation.definition.symbol.end.crystal"}},"name":"constant.other.symbol.crystal","patterns":[{"match":"\\\\\\\\[\'\\\\\\\\]","name":"constant.character.escape.crystal"}]},{"begin":":\\"","beginCaptures":{"0":{"name":"punctuation.section.symbol.begin.crystal"}},"end":"\\"","endCaptures":{"0":{"name":"punctuation.section.symbol.end.crystal"}},"name":"constant.other.symbol.interpolated.crystal","patterns":[{"include":"#interpolated_crystal"},{"include":"#escaped_char"}]},{"match":"(?<!\\\\()/=","name":"keyword.operator.assignment.augmented.crystal"},{"begin":"\'","beginCaptures":{"0":{"name":"punctuation.definition.string.begin.crystal"}},"end":"\'","endCaptures":{"0":{"name":"punctuation.definition.string.end.crystal"}},"name":"string.quoted.single.crystal","patterns":[{"match":"\\\\\\\\[\'\\\\\\\\]","name":"constant.character.escape.crystal"}]},{"begin":"\\"","beginCaptures":{"0":{"name":"punctuation.definition.string.begin.crystal"}},"end":"\\"","endCaptures":{"0":{"name":"punctuation.definition.string.end.crystal"}},"name":"string.quoted.double.interpolated.crystal","patterns":[{"include":"#interpolated_crystal"},{"include":"#escaped_char"}]},{"begin":"`","beginCaptures":{"0":{"name":"punctuation.definition.string.begin.crystal"}},"end":"`","endCaptures":{"0":{"name":"punctuation.definition.string.end.crystal"}},"name":"string.interpolated.crystal","patterns":[{"include":"#interpolated_crystal"},{"include":"#escaped_char"}]},{"begin":"%x\\\\{","beginCaptures":{"0":{"name":"punctuation.definition.string.begin.crystal"}},"end":"}","endCaptures":{"0":{"name":"punctuation.definition.string.end.crystal"}},"name":"string.interpolated.crystal","patterns":[{"include":"#interpolated_crystal"},{"include":"#escaped_char"},{"include":"#nest_curly_i"}]},{"begin":"%x\\\\[","beginCaptures":{"0":{"name":"punctuation.definition.string.begin.crystal"}},"end":"]","endCaptures":{"0":{"name":"punctuation.definition.string.end.crystal"}},"name":"string.interpolated.crystal","patterns":[{"include":"#interpolated_crystal"},{"include":"#escaped_char"},{"include":"#nest_brackets_i"}]},{"begin":"%x<","beginCaptures":{"0":{"name":"punctuation.definition.string.begin.crystal"}},"end":">","endCaptures":{"0":{"name":"punctuation.definition.string.end.crystal"}},"name":"string.interpolated.crystal","patterns":[{"include":"#interpolated_crystal"},{"include":"#escaped_char"},{"include":"#nest_ltgt_i"}]},{"begin":"%x\\\\(","beginCaptures":{"0":{"name":"punctuation.definition.string.begin.crystal"}},"end":"\\\\)","endCaptures":{"0":{"name":"punctuation.definition.string.end.crystal"}},"name":"string.interpolated.crystal","patterns":[{"include":"#interpolated_crystal"},{"include":"#escaped_char"},{"include":"#nest_parens_i"}]},{"begin":"%x\\\\|","beginCaptures":{"0":{"name":"punctuation.definition.string.begin.crystal"}},"end":"\\\\|","endCaptures":{"0":{"name":"punctuation.definition.string.end.crystal"}},"name":"string.interpolated.crystal","patterns":[{"include":"#interpolated_crystal"},{"include":"#escaped_char"}]},{"begin":"(?:^|(?<=[=>~(?:\\\\[,|\\\\&;]|[\\\\s;]if\\\\s|[\\\\s;]elsif\\\\s|[\\\\s;]while\\\\s|[\\\\s;]unless\\\\s|[\\\\s;]when\\\\s|[\\\\s;]assert_match\\\\s|[\\\\s;]or\\\\s|[\\\\s;]and\\\\s|[\\\\s;]not\\\\s|[\\\\s.]index\\\\s|[\\\\s.]scan\\\\s|[\\\\s.]sub\\\\s|[\\\\s.]sub!\\\\s|[\\\\s.]gsub\\\\s|[\\\\s.]gsub!\\\\s|[\\\\s.]match\\\\s)|(?<=^(?:when\\\\s|if\\\\s|elsif\\\\s|while\\\\s|unless\\\\s)))\\\\s*((/))(?![*+{}?])","captures":{"1":{"name":"string.regexp.classic.crystal"},"2":{"name":"punctuation.definition.string.crystal"}},"contentName":"string.regexp.classic.crystal","end":"((/[imsx]*))","patterns":[{"include":"#regex_sub"}]},{"begin":"%r\\\\{","beginCaptures":{"0":{"name":"punctuation.definition.string.begin.crystal"}},"end":"}[imsx]*","endCaptures":{"0":{"name":"punctuation.definition.string.end.crystal"}},"name":"string.regexp.mod-r.crystal","patterns":[{"include":"#regex_sub"},{"include":"#nest_curly_r"}]},{"begin":"%r\\\\[","beginCaptures":{"0":{"name":"punctuation.definition.string.begin.crystal"}},"end":"][imsx]*","endCaptures":{"0":{"name":"punctuation.definition.string.end.crystal"}},"name":"string.regexp.mod-r.crystal","patterns":[{"include":"#regex_sub"},{"include":"#nest_brackets_r"}]},{"begin":"%r\\\\(","beginCaptures":{"0":{"name":"punctuation.definition.string.begin.crystal"}},"end":"\\\\)[imsx]*","endCaptures":{"0":{"name":"punctuation.definition.string.end.crystal"}},"name":"string.regexp.mod-r.crystal","patterns":[{"include":"#regex_sub"},{"include":"#nest_parens_r"}]},{"begin":"%r<","beginCaptures":{"0":{"name":"punctuation.definition.string.begin.crystal"}},"end":">[imsx]*","endCaptures":{"0":{"name":"punctuation.definition.string.end.crystal"}},"name":"string.regexp.mod-r.crystal","patterns":[{"include":"#regex_sub"},{"include":"#nest_ltgt_r"}]},{"begin":"%r\\\\|","beginCaptures":{"0":{"name":"punctuation.definition.string.begin.crystal"}},"end":"\\\\|[imsx]*","endCaptures":{"0":{"name":"punctuation.definition.string.end.crystal"}},"name":"string.regexp.mod-r.crystal","patterns":[{"include":"#regex_sub"}]},{"begin":"%Q?\\\\(","beginCaptures":{"0":{"name":"punctuation.definition.string.begin.crystal"}},"end":"\\\\)","endCaptures":{"0":{"name":"punctuation.definition.string.end.crystal"}},"name":"string.quoted.other.literal.upper.crystal","patterns":[{"include":"#interpolated_crystal"},{"include":"#escaped_char"},{"include":"#nest_parens_i"}]},{"begin":"%Q?\\\\[","beginCaptures":{"0":{"name":"punctuation.definition.string.begin.crystal"}},"end":"]","endCaptures":{"0":{"name":"punctuation.definition.string.end.crystal"}},"name":"string.quoted.other.literal.upper.crystal","patterns":[{"include":"#interpolated_crystal"},{"include":"#escaped_char"},{"include":"#nest_brackets_i"}]},{"begin":"%Q?<","beginCaptures":{"0":{"name":"punctuation.definition.string.begin.crystal"}},"end":">","endCaptures":{"0":{"name":"punctuation.definition.string.end.crystal"}},"name":"string.quoted.other.literal.upper.crystal","patterns":[{"include":"#interpolated_crystal"},{"include":"#escaped_char"},{"include":"#nest_ltgt_i"}]},{"begin":"%Q?\\\\{","beginCaptures":{"0":{"name":"punctuation.definition.string.begin.crystal"}},"end":"}","endCaptures":{"0":{"name":"punctuation.definition.string.end.crystal"}},"name":"string.quoted.double.crystal.mod","patterns":[{"include":"#interpolated_crystal"},{"include":"#escaped_char"},{"include":"#nest_curly_i"}]},{"begin":"%Q\\\\|","beginCaptures":{"0":{"name":"punctuation.definition.string.begin.crystal"}},"end":"\\\\|","endCaptures":{"0":{"name":"punctuation.definition.string.end.crystal"}},"name":"string.quoted.other.literal.upper.crystal","patterns":[{"include":"#interpolated_crystal"},{"include":"#escaped_char"}]},{"begin":"%[qwi]\\\\(","beginCaptures":{"0":{"name":"punctuation.definition.string.begin.crystal"}},"end":"\\\\)","endCaptures":{"0":{"name":"punctuation.definition.string.end.crystal"}},"name":"string.quoted.other.literal.lower.crystal","patterns":[{"match":"\\\\\\\\[)\\\\\\\\]","name":"constant.character.escape.crystal"},{"include":"#nest_parens"}]},{"begin":"%[qwi]<","beginCaptures":{"0":{"name":"punctuation.definition.string.begin.crystal"}},"end":">","endCaptures":{"0":{"name":"punctuation.definition.string.end.crystal"}},"name":"string.quoted.other.literal.lower.crystal","patterns":[{"match":"\\\\\\\\[>\\\\\\\\]","name":"constant.character.escape.crystal"},{"include":"#nest_ltgt"}]},{"begin":"%[qwi]\\\\[","beginCaptures":{"0":{"name":"punctuation.definition.string.begin.crystal"}},"end":"]","endCaptures":{"0":{"name":"punctuation.definition.string.end.crystal"}},"name":"string.quoted.other.literal.lower.crystal","patterns":[{"match":"\\\\\\\\[\\\\]\\\\\\\\]","name":"constant.character.escape.crystal"},{"include":"#nest_brackets"}]},{"begin":"%[qwi]\\\\{","beginCaptures":{"0":{"name":"punctuation.definition.string.begin.crystal"}},"end":"}","endCaptures":{"0":{"name":"punctuation.definition.string.end.crystal"}},"name":"string.quoted.other.literal.lower.crystal","patterns":[{"match":"\\\\\\\\[}\\\\\\\\]","name":"constant.character.escape.crystal"},{"include":"#nest_curly"}]},{"begin":"%[qwi]\\\\|","beginCaptures":{"0":{"name":"punctuation.definition.string.begin.crystal"}},"end":"\\\\|","endCaptures":{"0":{"name":"punctuation.definition.string.end.crystal"}},"name":"string.quoted.other.literal.lower.crystal","patterns":[{"match":"\\\\\\\\."}]},{"captures":{"1":{"name":"punctuation.definition.constant.crystal"}},"match":"(?<!:)(:)(?>[a-zA-Z_\\\\x{80}-\\\\x{10FFFF}][\\\\w\\\\x{80}-\\\\x{10FFFF}]*(?>[?!]|=(?![>=]))?|===?|>[>=]?|<[<=]?|<=>|[%\\\\&`/|]|\\\\*\\\\*?|=?~|[\\\\-+]@?|\\\\[][?=]?|@@?[a-zA-Z_\\\\x{80}-\\\\x{10FFFF}][\\\\w\\\\x{80}-\\\\x{10FFFF}]*)","name":"constant.other.symbol.crystal"},{"captures":{"1":{"name":"punctuation.definition.constant.crystal"}},"match":"(?>[a-zA-Z_\\\\x{80}-\\\\x{10FFFF}][\\\\w\\\\x{80}-\\\\x{10FFFF}]*[?!]?)(:)(?!:)","name":"constant.other.symbol.crystal.19syntax"},{"captures":{"1":{"name":"punctuation.definition.comment.crystal"}},"match":"(?:^[ \\\\t]+)?(#).*$\\\\n?","name":"comment.line.number-sign.crystal"},{"match":"(?<!}})\\\\b_(\\\\w+[?!]?)\\\\b(?!\\\\()","name":"comment.unused.crystal"},{"begin":"(?><<-(\'?)((?:[_\\\\w]+_|)HTML)\\\\b\\\\1)","beginCaptures":{"0":{"name":"punctuation.definition.string.begin.crystal"}},"contentName":"text.html.embedded.crystal","end":"\\\\s*\\\\2\\\\b","endCaptures":{"0":{"name":"punctuation.definition.string.end.crystal"}},"name":"string.unquoted.embedded.html.crystal","patterns":[{"include":"#heredoc"},{"include":"text.html.basic"},{"include":"#interpolated_crystal"},{"include":"#escaped_char"}]},{"begin":"(?><<-(\'?)((?:[_\\\\w]+_|)SQL)\\\\b\\\\1)","beginCaptures":{"0":{"name":"punctuation.definition.string.begin.crystal"}},"contentName":"text.sql.embedded.crystal","end":"\\\\s*\\\\2\\\\b","endCaptures":{"0":{"name":"punctuation.definition.string.end.crystal"}},"name":"string.unquoted.embedded.sql.crystal","patterns":[{"include":"#heredoc"},{"include":"source.sql"},{"include":"#interpolated_crystal"},{"include":"#escaped_char"}]},{"begin":"(?><<-(\'?)((?:[_\\\\w]+_|)CSS)\\\\b\\\\1)","beginCaptures":{"0":{"name":"punctuation.definition.string.begin.crystal"}},"contentName":"text.css.embedded.crystal","end":"\\\\s*\\\\2\\\\b","endCaptures":{"0":{"name":"punctuation.definition.string.end.crystal"}},"name":"string.unquoted.embedded.css.crystal","patterns":[{"include":"#heredoc"},{"include":"source.css"},{"include":"#interpolated_crystal"},{"include":"#escaped_char"}]},{"begin":"(?><<-(\'?)((?:[_\\\\w]+_|)CPP)\\\\b\\\\1)","beginCaptures":{"0":{"name":"punctuation.definition.string.begin.crystal"}},"contentName":"text.c++.embedded.crystal","end":"\\\\s*\\\\2\\\\b","endCaptures":{"0":{"name":"punctuation.definition.string.end.crystal"}},"name":"string.unquoted.embedded.cplusplus.crystal","patterns":[{"include":"#heredoc"},{"include":"source.c++"},{"include":"#interpolated_crystal"},{"include":"#escaped_char"}]},{"begin":"(?><<-(\'?)((?:[_\\\\w]+_|)C)\\\\b\\\\1)","beginCaptures":{"0":{"name":"punctuation.definition.string.begin.crystal"}},"contentName":"text.c.embedded.crystal","end":"\\\\s*\\\\2\\\\b","endCaptures":{"0":{"name":"punctuation.definition.string.end.crystal"}},"name":"string.unquoted.embedded.c.crystal","patterns":[{"include":"#heredoc"},{"include":"source.c"},{"include":"#interpolated_crystal"},{"include":"#escaped_char"}]},{"begin":"(?><<-(\'?)((?:[_\\\\w]+_|)J(?:S|AVASCRIPT))\\\\b\\\\1)","beginCaptures":{"0":{"name":"punctuation.definition.string.begin.crystal"}},"contentName":"text.js.embedded.crystal","end":"\\\\s*\\\\2\\\\b","endCaptures":{"0":{"name":"punctuation.definition.string.end.crystal"}},"name":"string.unquoted.embedded.js.crystal","patterns":[{"include":"#heredoc"},{"include":"source.js"},{"include":"#interpolated_crystal"},{"include":"#escaped_char"}]},{"begin":"(?><<-(\'?)((?:[_\\\\w]+_|)JQUERY)\\\\b\\\\1)","beginCaptures":{"0":{"name":"punctuation.definition.string.begin.crystal"}},"contentName":"text.js.jquery.embedded.crystal","end":"\\\\s*\\\\2\\\\b","endCaptures":{"0":{"name":"punctuation.definition.string.end.crystal"}},"name":"string.unquoted.embedded.js.jquery.crystal","patterns":[{"include":"#heredoc"},{"include":"source.js.jquery"},{"include":"#interpolated_crystal"},{"include":"#escaped_char"}]},{"begin":"(?><<-(\'?)((?:[_\\\\w]+_|)SH(?:|ELL))\\\\b\\\\1)","beginCaptures":{"0":{"name":"punctuation.definition.string.begin.crystal"}},"contentName":"text.shell.embedded.crystal","end":"\\\\s*\\\\2\\\\b","endCaptures":{"0":{"name":"punctuation.definition.string.end.crystal"}},"name":"string.unquoted.embedded.shell.crystal","patterns":[{"include":"#heredoc"},{"include":"source.shell"},{"include":"#interpolated_crystal"},{"include":"#escaped_char"}]},{"begin":"(?><<-(\'?)((?:[_\\\\w]+_|)CRYSTAL)\\\\b\\\\1)","beginCaptures":{"0":{"name":"punctuation.definition.string.begin.crystal"}},"contentName":"text.crystal.embedded.crystal","end":"\\\\s*\\\\2\\\\b","endCaptures":{"0":{"name":"punctuation.definition.string.end.crystal"}},"name":"string.unquoted.embedded.crystal.crystal","patterns":[{"include":"#heredoc"},{"include":"source.crystal"},{"include":"#interpolated_crystal"},{"include":"#escaped_char"}]},{"begin":"(?><<-\'(\\\\w+)\')","beginCaptures":{"0":{"name":"punctuation.definition.string.begin.crystal"}},"end":"\\\\s*\\\\1\\\\b","endCaptures":{"0":{"name":"punctuation.definition.string.end.crystal"}},"name":"string.unquoted.heredoc.crystal","patterns":[{"include":"#heredoc"},{"include":"#escaped_char"}]},{"begin":"(?><<-(\\\\w+)\\\\b)","beginCaptures":{"0":{"name":"punctuation.definition.string.begin.crystal"}},"end":"\\\\s*\\\\1\\\\b","endCaptures":{"0":{"name":"punctuation.definition.string.end.crystal"}},"name":"string.unquoted.heredoc.crystal","patterns":[{"include":"#heredoc"},{"include":"#interpolated_crystal"},{"include":"#escaped_char"}]},{"begin":"(?<=\\\\{|\\\\{\\\\s|[^A-Za-z0-9_]do|^do|[^A-Za-z0-9_]do\\\\s|^do\\\\s)(\\\\|)","captures":{"1":{"name":"punctuation.separator.variable.crystal"}},"end":"(?<!\\\\|)(\\\\|)(?!\\\\|)","patterns":[{"include":"source.crystal"},{"match":"[_a-zA-Z][_a-zA-Z0-9]*","name":"variable.other.block.crystal"},{"match":",","name":"punctuation.separator.variable.crystal"}]},{"match":"=>","name":"punctuation.separator.key-value"},{"match":"->","name":"support.function.kernel.crystal"},{"match":"<<=|%=|&{1,2}=|\\\\*=|\\\\*\\\\*=|\\\\+=|-=|\\\\^=|\\\\|{1,2}=|<<","name":"keyword.operator.assignment.augmented.crystal"},{"match":"<=>|<(?![<=])|>(?![<=>])|<=|>=|===|==|=~|!=|!~|(?<=[ \\\\t])\\\\?","name":"keyword.operator.comparison.crystal"},{"match":"(?<=^|[ \\\\t])!|&&|\\\\|\\\\||\\\\^","name":"keyword.operator.logical.crystal"},{"match":"(\\\\{%|%}|\\\\{\\\\{|}})","name":"keyword.operator.macro.crystal"},{"captures":{"1":{"name":"punctuation.separator.method.crystal"}},"match":"(&\\\\.)\\\\s*(?![A-Z])"},{"match":"([%\\\\&]|\\\\*\\\\*|[*+\\\\-/])","name":"keyword.operator.arithmetic.crystal"},{"match":"=","name":"keyword.operator.assignment.crystal"},{"match":"[|~]|>>","name":"keyword.operator.other.crystal"},{"match":":","name":"punctuation.separator.other.crystal"},{"match":";","name":"punctuation.separator.statement.crystal"},{"match":",","name":"punctuation.separator.object.crystal"},{"match":"\\\\.|::","name":"punctuation.separator.method.crystal"},{"match":"[{}]","name":"punctuation.section.scope.crystal"},{"match":"[\\\\[\\\\]]","name":"punctuation.section.array.crystal"},{"match":"[()]","name":"punctuation.section.function.crystal"},{"begin":"(?=[a-zA-Z0-9_!?]+\\\\()","end":"(?<=\\\\))","name":"meta.function-call.crystal","patterns":[{"match":"([a-zA-Z0-9_!?]+)(?=\\\\()","name":"entity.name.function.crystal"},{"include":"$self"}]},{"match":"((?<=\\\\W)\\\\b|^)\\\\w+\\\\b(?=\\\\s*([\\\\])}=+\\\\-*/\\\\^$,.]|<\\\\s|<<[\\\\s|.]))","name":"variable.other.crystal"}],"repository":{"escaped_char":{"match":"\\\\\\\\(?:[0-7]{1,3}|x\\\\h{2}|u\\\\h{4}|u\\\\{[ \\\\h]+}|.)","name":"constant.character.escape.crystal"},"heredoc":{"begin":"^<<-?\\\\w+","end":"$","patterns":[{"include":"$self"}]},"interpolated_crystal":{"patterns":[{"begin":"#\\\\{","beginCaptures":{"0":{"name":"punctuation.section.embedded.begin.crystal"}},"contentName":"source.crystal","end":"(})","endCaptures":{"0":{"name":"punctuation.section.embedded.end.crystal"},"1":{"name":"source.crystal"}},"name":"meta.embedded.line.crystal","patterns":[{"include":"#nest_curly_and_self"},{"include":"$self"}],"repository":{"nest_curly_and_self":{"patterns":[{"begin":"\\\\{","captures":{"0":{"name":"punctuation.section.scope.crystal"}},"end":"}","patterns":[{"include":"#nest_curly_and_self"}]},{"include":"$self"}]}}},{"captures":{"1":{"name":"punctuation.definition.variable.crystal"}},"match":"(#@)[a-zA-Z_]\\\\w*","name":"variable.other.readwrite.instance.crystal"},{"captures":{"1":{"name":"punctuation.definition.variable.crystal"}},"match":"(#@@)[a-zA-Z_]\\\\w*","name":"variable.other.readwrite.class.crystal"},{"captures":{"1":{"name":"punctuation.definition.variable.crystal"}},"match":"(#\\\\$)[a-zA-Z_]\\\\w*","name":"variable.other.readwrite.global.crystal"}]},"nest_brackets":{"begin":"\\\\[","captures":{"0":{"name":"punctuation.section.scope.crystal"}},"end":"]","patterns":[{"include":"#nest_brackets"}]},"nest_brackets_i":{"begin":"\\\\[","captures":{"0":{"name":"punctuation.section.scope.crystal"}},"end":"]","patterns":[{"include":"#interpolated_crystal"},{"include":"#escaped_char"},{"include":"#nest_brackets_i"}]},"nest_brackets_r":{"begin":"\\\\[","captures":{"0":{"name":"punctuation.section.scope.crystal"}},"end":"]","patterns":[{"include":"#regex_sub"},{"include":"#nest_brackets_r"}]},"nest_curly":{"begin":"\\\\{","captures":{"0":{"name":"punctuation.section.scope.crystal"}},"end":"}","patterns":[{"include":"#nest_curly"}]},"nest_curly_and_self":{"patterns":[{"begin":"\\\\{","captures":{"0":{"name":"punctuation.section.scope.crystal"}},"end":"}","patterns":[{"include":"#nest_curly_and_self"}]},{"include":"$self"}]},"nest_curly_i":{"begin":"\\\\{","captures":{"0":{"name":"punctuation.section.scope.crystal"}},"end":"}","patterns":[{"include":"#interpolated_crystal"},{"include":"#escaped_char"},{"include":"#nest_curly_i"}]},"nest_curly_r":{"begin":"\\\\{","captures":{"0":{"name":"punctuation.section.scope.crystal"}},"end":"}","patterns":[{"include":"#regex_sub"},{"include":"#nest_curly_r"}]},"nest_ltgt":{"begin":"<","captures":{"0":{"name":"punctuation.section.scope.crystal"}},"end":">","patterns":[{"include":"#nest_ltgt"}]},"nest_ltgt_i":{"begin":"<","captures":{"0":{"name":"punctuation.section.scope.crystal"}},"end":">","patterns":[{"include":"#interpolated_crystal"},{"include":"#escaped_char"},{"include":"#nest_ltgt_i"}]},"nest_ltgt_r":{"begin":"<","captures":{"0":{"name":"punctuation.section.scope.crystal"}},"end":">","patterns":[{"include":"#regex_sub"},{"include":"#nest_ltgt_r"}]},"nest_parens":{"begin":"\\\\(","captures":{"0":{"name":"punctuation.section.scope.crystal"}},"end":"\\\\)","patterns":[{"include":"#nest_parens"}]},"nest_parens_i":{"begin":"\\\\(","captures":{"0":{"name":"punctuation.section.scope.crystal"}},"end":"\\\\)","patterns":[{"include":"#interpolated_crystal"},{"include":"#escaped_char"},{"include":"#nest_parens_i"}]},"nest_parens_r":{"begin":"\\\\(","captures":{"0":{"name":"punctuation.section.scope.crystal"}},"end":"\\\\)","patterns":[{"include":"#regex_sub"},{"include":"#nest_parens_r"}]},"regex_sub":{"patterns":[{"include":"#interpolated_crystal"},{"include":"#escaped_char"},{"captures":{"1":{"name":"punctuation.definition.arbitrary-repetition.crystal"},"3":{"name":"punctuation.definition.arbitrary-repetition.crystal"}},"match":"(\\\\{)\\\\d+(,\\\\d+)?(})","name":"string.regexp.arbitrary-repetition.crystal"},{"begin":"\\\\[(?:\\\\^?])?","captures":{"0":{"name":"punctuation.definition.character-class.crystal"}},"end":"]","name":"string.regexp.character-class.crystal","patterns":[{"include":"#escaped_char"}]},{"begin":"\\\\(","captures":{"0":{"name":"punctuation.definition.group.crystal"}},"end":"\\\\)","name":"string.regexp.group.crystal","patterns":[{"include":"#regex_sub"}]},{"captures":{"1":{"name":"punctuation.definition.comment.crystal"}},"match":"(?<=^|\\\\s)(#)\\\\s[a-zA-Z0-9,. \\\\t?!\\\\-[^\\\\x00-\\\\x7F]]*$","name":"comment.line.number-sign.crystal"}]}},"scopeName":"source.crystal","embeddedLangs":["html","sql","css","c","javascript","shellscript"]}')),d=[...a.default,...r.default,...s.default,...i.default,...c.default,...l.default,u]}}]);