# 1. Image PHP avec extensions nécessaires
FROM php:8.3-fpm

# 2. Installer les dépendances système et PHP
RUN apt-get update && apt-get install -y \
    git \
    unzip \
    libpq-dev \
    libzip-dev \
    zip \
    && docker-php-ext-install pdo pdo_pgsql zip

# 3. Installer Composer
COPY --from=composer:2 /usr/bin/composer /usr/bin/composer

# 4. Définir le répertoire de travail
WORKDIR /var/www/symfony

# 5. Copier seulement les fichiers nécessaires pour composer
COPY composer.json composer.lock ./

# 6. Installer les dépendances PHP sans dev et avec autoload optimisé
RUN composer install --no-dev --optimize-autoloader

# 7. Copier le reste de l’application
COPY . .

# 8. Nettoyer le cache, créer les dossiers var et logs avec permissions correctes
RUN mkdir -p var/cache var/log var/sessions \
    && chown -R www-data:www-data var/cache var/log var/sessions

# 9. Mettre l'environnement en production
ENV APP_ENV=prod
ENV APP_DEBUG=0

# 10. Exposer le port de PHP-FPM
EXPOSE 9000

# 11. Commande par défaut
CMD ["php-fpm"]
