# 1️⃣ Image de base
FROM php:8.3-fpm

# 2️⃣ Installer les dépendances système et PHP nécessaires
RUN apt-get update && apt-get install -y \
    git \
    unzip \
    libpq-dev \
    libzip-dev \
    zip \
    && docker-php-ext-install pdo pdo_pgsql zip

# 3️⃣ Installer Composer
COPY --from=composer:latest /usr/bin/composer /usr/bin/composer

# 4️⃣ Définir le répertoire de travail
WORKDIR /var/www/symfony

# 5️⃣ Copier les fichiers
COPY . .

# 6️⃣ Créer les dossiers nécessaires et ajuster permissions
RUN mkdir -p var/cache var/log var/sessions \
    && chown -R www-data:www-data var/cache var/log var/sessions

# 7️⃣ Installer les dépendances PHP (production)
RUN composer install --no-dev --optimize-autoloader

# 8️⃣ Définir l’utilisateur
USER www-data

# 9️⃣ Exposer le port (Render utilisera par défaut 8080 pour le web)
EXPOSE 8080

# 🔟 Commande pour lancer le serveur PHP-FPM
CMD ["php-fpm"]
