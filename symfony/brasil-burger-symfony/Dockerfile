# Utilisez PHP 8.2 explicitement
FROM php:8.2-apache

# Installer les extensions nécessaires
RUN apt-get update && apt-get install -y \
    libpq-dev \
    libzip-dev \
    libonig-dev \
    zip \
    unzip \
    git \
    && docker-php-ext-install \
        pdo \
        pdo_pgsql \
        zip \
        mbstring \
        intl \
        bcmath

# Activer Apache mod_rewrite
RUN a2enmod rewrite

# Installer Composer
COPY --from=composer:latest /usr/bin/composer /usr/bin/composer

# Définir le répertoire de travail
WORKDIR /var/www/html

# Copier d'abord composer.json pour cache
COPY composer.json composer.lock symfony.lock ./

# Installer les dépendances (sans scripts)
RUN composer install --no-dev --no-scripts --optimize-autoloader

# Copier tout le reste
COPY . .

# Exécuter les scripts Symfony
RUN composer run-script post-install-cmd

# Configurer les permissions
RUN chown -R www-data:www-data /var/www/html/var

# Port Render
EXPOSE 8080

# Commande de démarrage
CMD ["apache2-foreground"]