import { defineConfig } from 'vite';
import react from "@vitejs/plugin-react-swc";

export default defineConfig(() => {
    return {
        esbuild: {
          loader: 'jsx'
        },
        optimizeDeps: {
            esbuildOptions: {
                loader: {
                    '.js': 'jsx',
                }
            }
        },
        build: {
            outDir: 'build',
        },
        plugins: [react()],
    };
});