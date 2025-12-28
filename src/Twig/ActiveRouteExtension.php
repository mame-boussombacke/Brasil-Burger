<?php

namespace App\Twig;

use Symfony\Component\HttpFoundation\RequestStack;
use Twig\Extension\AbstractExtension;
use Twig\TwigFunction;

class ActiveRouteExtension extends AbstractExtension
{
    private RequestStack $requestStack;

    public function __construct(RequestStack $requestStack)
    {
        $this->requestStack = $requestStack;
    }

    public function getFunctions(): array
    {
        return [
            new TwigFunction('active_class', [$this, 'getActiveClass']),
            new TwigFunction('is_active_route', [$this, 'isActiveRoute']),
        ];
    }

    /**
     * Retourne 'active' si la route courante commence par $routePrefix
     * Exemple d'utilisation: {{ active_class('client') }} ou {{ active_class('client_index') }}
     */
    public function getActiveClass(string $routePrefix, string $class = 'active'): string
    {
        return $this->isActiveRoute($routePrefix) ? $class : '';
    }

    /**
     * Retourne vrai si la route actuelle commence par $routePrefix
     */
    public function isActiveRoute(string $routePrefix): bool
    {
        $request = $this->requestStack->getCurrentRequest();
        if (!$request) {
            return false;
        }

        $current = $request->attributes->get('_route');
        if (!$current) {
            return false;
        }

        // if exact match
        if ($current === $routePrefix) {
            return true;
        }

        // if prefix match (e.g. 'client' matches 'client_index', 'client_new')
        return str_starts_with($current, $routePrefix . '_');
    }
}
