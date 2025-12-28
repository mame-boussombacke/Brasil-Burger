# Étape 1 : Image PHP officielle avec extensions nécessaires
FROM php:8.3-fpm

# Installer les dépendances système et extensions PHP
RUN apt-get update && apt-get install -y \
    git \
    unzip \
    libpq-dev \
    libzip-dev \
    libicu-dev \
    zlib1g-dev \
    && docker-php-ext-install pdo pdo_pgsql intl zip opcache

# Installer Composer
COPY --from=composer:2 /usr/bin/composer /usr/bin/composer

# Définir le répertoire de travail
WORKDIR /var/www/symfony

# Copier uniquement les fichiers composer pour installer les dépendances
COPY composer.json composer.lock ./

# Installer les dépendances sans les packages de dev
ENV APP_ENV=prod
ENV APP_DEBUG=0
RUN composer install --no-dev --optimize-autoloader

# Copier le reste du projet
COPY . .

# Construire le cache Symfony en prod
RUN php bin/console cache:clear --env=prod --no-debug

# Exposer le port pour PHP-FPM
EXPOSE 9000

# Commande par défaut
CMD ["php-fpm"]
