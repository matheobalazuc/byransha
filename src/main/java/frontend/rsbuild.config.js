import { defineConfig } from '@rsbuild/core';
import { pluginReact } from '@rsbuild/plugin-react';

export default defineConfig({
    html: {
        template: './index.html',
    },
    source: {
        entry: {
            index: './src/index.jsx'
        }
    },
    output: {
        cleanDistPath: process.env.NODE_ENV === 'production',
        distPath: {
            root: '../../../../build/frontend'
        }
    },
    server: {
        port: 5173
    },
    plugins: [pluginReact()],
});