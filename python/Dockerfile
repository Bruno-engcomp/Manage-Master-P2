FROM python:3.11-slim

LABEL authors="bruno"
LABEL description="API Python para gráficos e relatórios do ManageMaster"

WORKDIR /app

# Instalar dependências do sistema
RUN apt-get update && apt-get install -y \
    gcc \
    g++ \
    && rm -rf /var/lib/apt/lists/*


COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt


COPY . .


EXPOSE 5000


ENV PORT=5000
ENV SPRING_BOOT_URL=http://host.docker.internal:8080
ENV DEBUG=False

CMD ["python", "app.py"]
