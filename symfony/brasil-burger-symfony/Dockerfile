# Dockerfile
FROM php:8.2-apache

# 1. Mettre à jour et installer les dépendances
RUN apt-get update && apt-get install -y \
    libpq-dev \
    libzip-dev \
    libonig-dev \
    libicu-dev \
    zip \
    unzip \
    git \
    curl \
    && rm -rf /var/lib/apt/lists/*

# 2. Installer les extensions PHP
RUN docker-php-ext-configure intl \
    && docker-php-ext-install \
        pdo \
        pdo_pgsql \
        zip \
        mbstring \
        intl \
        bcmath \
        opcache

# 3. Activer les modules Apache
RUN a2enmod rewrite headers

# 4. Installer Composer
COPY --from=composer:latest /usr/bin/composer /usr/bin/composer

# 5. Définir le répertoire de travail
WORKDIR /var/www/html

# 6. Copier les fichiers du projet
COPY . .

# 7. Configurer les permissions
RUN chown -R www-data:www-data /var/www/html \
    && chmod -R 755 /var/www/html \
    && chmod -R 777 /var/www/html/var

# 8. Installer les dépendances Composer (en production)
# Décommentez si vous voulez installer automatiquement :
# RUN composer install --no-dev --no-progress --optimize-autoloader

# 9. Exposer le port
EXPOSE 80

# 10. Commande par défaut
CMD ["apache2-foreground"]