# Simulated Annealing Optimizer

<p align="center">
	<img src="https://img.shields.io/github/last-commit/JosueVazqJim/Simulated-Annealing?style=for-the-badge&logo=git&logoColor=white&color=ff0000" alt="last-commit">
	<img src="https://img.shields.io/github/languages/top/JosueVazqJim/Simulated-Annealing?style=for-the-badge&color=ff0000" alt="repo-top-language">
</p>
<p align="center">Built with the tools and technologies:</p>
<p align="center">
	<img src="https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white" alt="java">
</p>

![Continuous Optimization Example](/docs/images/app_runnin_parameters_continue.png)
![Discrete Optimization Example](/docs/images/app_runnin_parameters_discrete.png)

## Overview
Java implementation of the Simulated Annealing algorithm for single-variable optimization problems, supporting both continuous and discrete search spaces. The algorithm efficiently finds global optima while avoiding local traps through controlled temperature cooling.

## Key Features
- ✅ **Dual-mode support**: Continuous and discrete problems
- ✅ **Customizable parameters**: Full control over optimization process
- ✅ **Mathematical expression parsing**: Supports complex objective functions
- ✅ **Lightweight**: Pure Java implementation with minimal dependencies
- ✅ **CLI interface**: Easy integration with automated workflows

## Getting Started

### Prerequisites
- Java JDK 11+
- Maven 3.6+

### Installation
```bash
git clone https://github.com/yourusername/Simulated-Annealing.git
cd Simulated-Annealing
mvn clean package
```


## Configuration Options

| Parameter         | Description                     | Default Value     |
|-------------------|---------------------------------|-------------------|
| `--function`      | Objective function              | `x^4-16x^2+5x`    |
| `--domain`        | Search space bounds (min max)   | `-5 5`            |
| `--type`          | Problem type (discrete/continuous) | `continuous`    |
| `--goal`          | Optimization target (min/max)   | `min`             |
| `--temp-init`     | Initial temperature             | `1000`            |
| `--temp-min`      | Minimum temperature             | `1`               |
| `--cooling-rate`  | Cooling factor (0 < rate < 1)   | `0.95`            |
| `--iterations`    | Iterations per temperature level | `1000`           |

## Example Functions

1. **Convex function**: `x^2 + 2x + 1`
2. **Multi-modal**: `x*sin(x^2) + x^2/10`
3. **Custom expression**: Any valid math expression using `x`

## Output

The program provides:
- Optimal solution found
- Objective function value
- Convergence statistics
- Optional visualization (requires Python matplotlib)

## Performance Tips

- For continuous problems, use higher iteration counts
- Start with higher temperatures (1000+) for complex landscapes
- Adjust cooling rate between 0.9-0.99 for balance

## Contributing

We welcome contributions! Please:

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request