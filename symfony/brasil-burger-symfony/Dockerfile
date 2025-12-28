# Étape 1 : Builder
FROM php:8.3-fpm-alpine AS builder

# Installer les dépendances système nécessaires
RUN apk add --no-cache \
    bash \
    git \
    unzip \
    libzip-dev \
    oniguruma-dev \
    icu-dev \
    curl \
    openssl \
    zlib-dev \
    autoconf \
    gcc \
    g++ \
    make \
    pkgconfig \
    postgresql-dev \
    mysql-client \
    && docker-php-ext-install intl pdo pdo_mysql pdo_pgsql zip opcache

# Installer Composer
COPY --from=composer:2 /usr/bin/composer /usr/bin/composer

# Copier le code
WORKDIR /app
COPY . .

# Installer les dépendances PHP sans dev
RUN composer install --no-dev --optimize-autoloader --no-interaction

# Générer le cache prod
RUN php bin/console cache:clear --env=prod --no-debug \
    && php bin/console cache:warmup --env=prod

# Fix des permissions
RUN mkdir -p var/cache var/log var/sessions \
    && chown -R www-data:www-data var

# Étape 2 : Image finale
FROM php:8.3-fpm-alpine

# Installer les extensions PHP nécessaires
RUN apk add --no-cache \
    libzip-dev \
    icu-dev \
    oniguruma-dev \
    postgresql-dev \
    mysql-client \
    && docker-php-ext-install intl pdo pdo_mysql pdo_pgsql zip opcache

# Copier les fichiers depuis le builder
WORKDIR /app
COPY --from=builder /app /app

# Changer l'utilisateur pour www-data
USER www-data

# Exposer le port Render
EXPOSE 10000

# Commande pour démarrer le serveur en prod
CMD ["php", "-S", "0.0.0.0:10000", "-t", "public"]
