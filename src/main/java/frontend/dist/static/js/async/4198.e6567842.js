"use strict";(self.webpackChunklogin_page=self.webpackChunklogin_page||[]).push([["4198"],{29792:function(e,n,t){t.r(n),t.d(n,{default:()=>i});let i=[Object.freeze(JSON.parse('{"displayName":"Prisma","fileTypes":["prisma"],"name":"prisma","patterns":[{"include":"#triple_comment"},{"include":"#double_comment"},{"include":"#multi_line_comment"},{"include":"#model_block_definition"},{"include":"#config_block_definition"},{"include":"#enum_block_definition"},{"include":"#type_definition"}],"repository":{"array":{"begin":"\\\\[","beginCaptures":{"1":{"name":"punctuation.definition.tag.prisma"}},"end":"]","endCaptures":{"1":{"name":"punctuation.definition.tag.prisma"}},"name":"source.prisma.array","patterns":[{"include":"#value"}]},"assignment":{"patterns":[{"begin":"^\\\\s*(\\\\w+)\\\\s*(=)\\\\s*","beginCaptures":{"1":{"name":"variable.other.assignment.prisma"},"2":{"name":"keyword.operator.terraform"}},"end":"\\\\n","patterns":[{"include":"#value"},{"include":"#double_comment_inline"}]}]},"attribute":{"captures":{"1":{"name":"entity.name.function.attribute.prisma"}},"match":"(@@?[\\\\w.]+)","name":"source.prisma.attribute"},"attribute_with_arguments":{"begin":"(@@?[\\\\w.]+)(\\\\()","beginCaptures":{"1":{"name":"entity.name.function.attribute.prisma"},"2":{"name":"punctuation.definition.tag.prisma"}},"end":"\\\\)","endCaptures":{"0":{"name":"punctuation.definition.tag.prisma"}},"name":"source.prisma.attribute.with_arguments","patterns":[{"include":"#named_argument"},{"include":"#value"}]},"boolean":{"match":"\\\\b(true|false)\\\\b","name":"constant.language.boolean.prisma"},"config_block_definition":{"begin":"^\\\\s*(generator|datasource)\\\\s+([A-Za-z]\\\\w*)\\\\s+(\\\\{)","beginCaptures":{"1":{"name":"storage.type.config.prisma"},"2":{"name":"entity.name.type.config.prisma"},"3":{"name":"punctuation.definition.tag.prisma"}},"end":"\\\\s*}","endCaptures":{"1":{"name":"punctuation.definition.tag.prisma"}},"name":"source.prisma.embedded.source","patterns":[{"include":"#triple_comment"},{"include":"#double_comment"},{"include":"#multi_line_comment"},{"include":"#assignment"}]},"double_comment":{"begin":"//","end":"$\\\\n?","name":"comment.prisma"},"double_comment_inline":{"match":"//[^\\\\n]*","name":"comment.prisma"},"double_quoted_string":{"begin":"\\"","beginCaptures":{"0":{"name":"string.quoted.double.start.prisma"}},"end":"\\"","endCaptures":{"0":{"name":"string.quoted.double.end.prisma"}},"name":"unnamed","patterns":[{"include":"#string_interpolation"},{"match":"([\\\\w\\\\-/._\\\\\\\\%@:?=]+)","name":"string.quoted.double.prisma"}]},"enum_block_definition":{"begin":"^\\\\s*(enum)\\\\s+([A-Za-z]\\\\w*)\\\\s+(\\\\{)","beginCaptures":{"1":{"name":"storage.type.enum.prisma"},"2":{"name":"entity.name.type.enum.prisma"},"3":{"name":"punctuation.definition.tag.prisma"}},"end":"\\\\s*}","endCaptures":{"0":{"name":"punctuation.definition.tag.prisma"}},"name":"source.prisma.embedded.source","patterns":[{"include":"#triple_comment"},{"include":"#double_comment"},{"include":"#multi_line_comment"},{"include":"#enum_value_definition"}]},"enum_value_definition":{"patterns":[{"captures":{"1":{"name":"variable.other.assignment.prisma"}},"match":"^\\\\s*(\\\\w+)\\\\s*"},{"include":"#attribute_with_arguments"},{"include":"#attribute"}]},"field_definition":{"name":"scalar.field","patterns":[{"captures":{"1":{"name":"variable.other.assignment.prisma"},"2":{"name":"invalid.illegal.colon.prisma"},"3":{"name":"variable.language.relations.prisma"},"4":{"name":"support.type.primitive.prisma"},"5":{"name":"keyword.operator.list_type.prisma"},"6":{"name":"keyword.operator.optional_type.prisma"},"7":{"name":"invalid.illegal.required_type.prisma"}},"match":"^\\\\s*(\\\\w+)(\\\\s*:)?\\\\s+((?!(?:Int|BigInt|String|DateTime|Bytes|Decimal|Float|Json|Boolean)\\\\b)\\\\b\\\\w+)?(Int|BigInt|String|DateTime|Bytes|Decimal|Float|Json|Boolean)?(\\\\[])?(\\\\?)?(!)?"},{"include":"#attribute_with_arguments"},{"include":"#attribute"}]},"functional":{"begin":"(\\\\w+)(\\\\()","beginCaptures":{"1":{"name":"support.function.functional.prisma"},"2":{"name":"punctuation.definition.tag.prisma"}},"end":"\\\\)","endCaptures":{"0":{"name":"punctuation.definition.tag.prisma"}},"name":"source.prisma.functional","patterns":[{"include":"#value"}]},"identifier":{"patterns":[{"match":"\\\\b(\\\\w)+\\\\b","name":"support.constant.constant.prisma"}]},"literal":{"name":"source.prisma.literal","patterns":[{"include":"#boolean"},{"include":"#number"},{"include":"#double_quoted_string"},{"include":"#identifier"}]},"map_key":{"name":"source.prisma.key","patterns":[{"captures":{"1":{"name":"variable.parameter.key.prisma"},"2":{"name":"punctuation.definition.separator.key-value.prisma"}},"match":"(\\\\w+)\\\\s*(:)\\\\s*"}]},"model_block_definition":{"begin":"^\\\\s*(model|type|view)\\\\s+([A-Za-z]\\\\w*)\\\\s*(\\\\{)","beginCaptures":{"1":{"name":"storage.type.model.prisma"},"2":{"name":"entity.name.type.model.prisma"},"3":{"name":"punctuation.definition.tag.prisma"}},"end":"\\\\s*}","endCaptures":{"0":{"name":"punctuation.definition.tag.prisma"}},"name":"source.prisma.embedded.source","patterns":[{"include":"#triple_comment"},{"include":"#double_comment"},{"include":"#multi_line_comment"},{"include":"#field_definition"}]},"multi_line_comment":{"begin":"/\\\\*","end":"\\\\*/","name":"comment.prisma"},"named_argument":{"name":"source.prisma.named_argument","patterns":[{"include":"#map_key"},{"include":"#value"}]},"number":{"match":"((0([xX])\\\\h*)|([+\\\\-])?\\\\b(([0-9]+\\\\.?[0-9]*)|(\\\\.[0-9]+))(([eE])([+\\\\-])?[0-9]+)?)([LlFfUuDdg]|UL|ul)?\\\\b","name":"constant.numeric.prisma"},"string_interpolation":{"patterns":[{"begin":"\\\\$\\\\{","beginCaptures":{"0":{"name":"keyword.control.interpolation.start.prisma"}},"end":"\\\\s*}","endCaptures":{"0":{"name":"keyword.control.interpolation.end.prisma"}},"name":"source.tag.embedded.source.prisma","patterns":[{"include":"#value"}]}]},"triple_comment":{"begin":"///","end":"$\\\\n?","name":"comment.prisma"},"type_definition":{"patterns":[{"captures":{"1":{"name":"storage.type.type.prisma"},"2":{"name":"entity.name.type.type.prisma"},"3":{"name":"support.type.primitive.prisma"}},"match":"^\\\\s*(type)\\\\s+(\\\\w+)\\\\s*=\\\\s*(\\\\w+)"},{"include":"#attribute_with_arguments"},{"include":"#attribute"}]},"value":{"name":"source.prisma.value","patterns":[{"include":"#array"},{"include":"#functional"},{"include":"#literal"}]}},"scopeName":"source.prisma"}'))]}}]);