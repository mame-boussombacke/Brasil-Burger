# Utiliser une image PHP avec Apache
FROM php:8.3-apache

# Installer les extensions PHP nécessaires pour Symfony et PostgreSQL
RUN apt-get update && apt-get install -y \
    libpq-dev \
    unzip \
    git \
    curl \
    && docker-php-ext-install pdo pdo_pgsql

# Activer le module rewrite pour Symfony
RUN a2enmod rewrite

# Installer Composer
COPY --from=composer:2 /usr/bin/composer /usr/bin/composer

# Copier tout le projet dans le conteneur
COPY . /var/www/html/

# Définir le répertoire de travail
WORKDIR /var/www/html

# Installer les dépendances Symfony sans les dev et optimiser l'autoload
RUN composer install --no-dev --optimize-autoloader

# Permissions pour cache/logs
RUN chown -R www-data:www-data /var/www/html/var

# Exposer le port 80 pour le serveur Apache
EXPOSE 80

# Commande pour démarrer Apache en mode foreground
CMD ["apache2-foreground"]
