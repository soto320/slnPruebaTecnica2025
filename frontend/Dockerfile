# --- build ---
FROM node:20-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm ci
COPY . .
# si usas el proxy nginx, compila apuntando a /api (valor por defecto)
ENV VITE_API_BASE_URL=${VITE_API_BASE_URL:-/api}
RUN npm run build

# --- serve ---
FROM nginx:alpine
COPY --from=build /app/dist /usr/share/nginx/html
# nginx con proxy /api -> app:8080 y fallback SPA
COPY default.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
