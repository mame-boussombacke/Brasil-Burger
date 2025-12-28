# 1️⃣ Image PHP avec Apache
FROM php:8.3-apache

# 2️⃣ Installation des extensions PHP nécessaires
RUN apt-get update && apt-get install -y \
    git \
    unzip \
    libicu-dev \
    libpq-dev \
    zlib1g-dev \
    libzip-dev \
    && docker-php-ext-install intl pdo pdo_pgsql zip opcache

# 3️⃣ Activer Apache mod_rewrite
RUN a2enmod rewrite

# 4️⃣ Installer Composer
COPY --from=composer:2 /usr/bin/composer /usr/bin/composer

# 5️⃣ Définir le répertoire de travail
WORKDIR /var/www/html

# 6️⃣ Copier les fichiers de l'application
COPY . .

# 7️⃣ Installer les dépendances Symfony
# Option A : pour prod, sans bundles dev
RUN composer install --no-dev --optimize-autoloader

# 8️⃣ Donner les droits aux dossiers cache et log
RUN chown -R www-data:www-data var/cache var/log var/sessions

# 9️⃣ Exposer le port Apache
EXPOSE 80

# 10️⃣ Commande par défaut
CMD ["apache2-foreground"]
